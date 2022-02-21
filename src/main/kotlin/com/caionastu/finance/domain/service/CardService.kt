package com.caionastu.finance.domain.service

import com.caionastu.finance.application.web.model.request.CardFilterRequest
import com.caionastu.finance.domain.entity.CardDocument
import com.caionastu.finance.domain.entity.TransactionDocument
import com.caionastu.finance.domain.exception.BusinessException
import com.caionastu.finance.domain.exception.NotFoundException
import com.caionastu.finance.domain.repository.CardRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CardService(
    private val repository: CardRepository,
) {

    fun findAll(pageable: Pageable, filterRequest: CardFilterRequest) =
        repository.findAllByDeleted(pageable, filterRequest.deleted)

    fun findById(id: String, deleted: Boolean = false) = repository.findByIdAndDeleted(id, deleted)
        ?: throw NotFoundException("card.exception.notFound", id)

    fun create(card: CardDocument): CardDocument {
        validateCard(card)
        return repository.save(card)
    }

    fun update(card: CardDocument): CardDocument =
        findById(card.id).let {
            validateCard(card)
            repository.save(card)
        }

    fun delete(id: String) {
        repository.findByIdOrNull(id)?.run {
            when (this.deleted) {
                true -> throw NotFoundException("card.exception.notFound", id)
                false -> repository.save(this.copy(deleted = true))
            }
        } ?: throw NotFoundException("card.exception.notFound", id)
    }

    fun transfer(transaction: TransactionDocument) {
        findById(transaction.referenceId).run {
            add(this, transaction).apply {
                repository.save(this)
            }
        }
    }

    fun reversal(transaction: TransactionDocument) {
        findById(transaction.referenceId).run {
            subtract(this, transaction)
                .apply {
                    repository.save(this)
                }
        }
    }

    fun updateTransaction(oldTransaction: TransactionDocument, newTransaction: TransactionDocument) {
        findById(oldTransaction.referenceId).run {
            val updatedCard =
                add(
                    subtract(this, oldTransaction),
                    newTransaction
                )
            repository.save(updatedCard)
        }
    }

    private fun add(card: CardDocument, transaction: TransactionDocument) =
        when (card.hasLimit(transaction.value)) {
            true -> card.copy(currentLimit = card.currentLimit.plus(transaction.value))
            false -> throw BusinessException("card.exception.limit.exceeded")
        }

    private fun subtract(card: CardDocument, transaction: TransactionDocument) =
        card.copy(currentLimit = card.currentLimit.minus(transaction.value))

    private fun validateCard(card: CardDocument) {
        if (repository.existsByFinalNumberAndDeleted(card.finalNumber)) {
            throw BusinessException("card.exception.finalNumber.exists")
        }
    }

}