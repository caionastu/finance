package com.caionastu.finance.domain.service

import com.caionastu.finance.application.web.model.request.CardFilterRequest
import com.caionastu.finance.domain.entity.CardDocument
import com.caionastu.finance.domain.exception.BusinessException
import com.caionastu.finance.domain.exception.NotFoundException
import com.caionastu.finance.domain.repository.AccountRepository
import com.caionastu.finance.domain.repository.CardRepository
import com.caionastu.finance.resources.isPositive
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CardService(
    private val repository: CardRepository,
    private val accountRepository: AccountRepository
) {

    fun findAll(pageable: Pageable, filterRequest: CardFilterRequest) =
        repository.findAllByDeleted(pageable, filterRequest.deleted)

    fun findById(id: String) = repository.findByIdAndDeleted(id)
        ?: throw NotFoundException("card.exception.notFound", id)

    fun create(card: CardDocument): CardDocument {
        validateCard(card)
        return repository.save(card)
    }

    fun update(card: CardDocument): CardDocument =
        repository.findByIdAndDeleted(card.id)?.let {
            validateCard(card)
            repository.save(card)
        } ?: throw NotFoundException("card.exception.notFound", card.id)

    fun delete(id: String) {
        repository.findByIdOrNull(id)?.run {
            when (this.deleted) {
                true -> throw NotFoundException("card.exception.notFound", id)
                false -> repository.save(this.copy(deleted = true))
            }
        } ?: throw NotFoundException("card.exception.notFound", id)
    }

    private fun validateCard(card: CardDocument) {
        if (repository.existsByFinalNumberAndDeleted(card.finalNumber)) {
            throw BusinessException("card.exception.finalNumber.exists")
        }

        when {
            card.isDebit() -> validateDebitCard(card)
            else -> validateCreditCard(card)
        }
    }

    private fun validateDebitCard(card: CardDocument) {
        card.debitAccountId?.run {

            if (!accountRepository.existsById(card.debitAccountId)) {
                throw NotFoundException("account.exception.notFound")
            }

        } ?: throw BusinessException("card.exception.debit")
    }

    fun validateCreditCard(card: CardDocument) {
        card.creditLimit?.run {
            if (!this.isPositive()) {
                throw BusinessException("card.creditLimit.positive")
            }
        } ?: throw BusinessException("card.creditLimit.empty")
    }
}