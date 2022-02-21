package com.caionastu.finance.domain.service

import com.caionastu.finance.application.web.model.request.TransactionFilterRequest
import com.caionastu.finance.domain.entity.TransactionDocument
import com.caionastu.finance.domain.exception.NotFoundException
import com.caionastu.finance.domain.repository.TransactionRepository
import com.caionastu.finance.domain.repository.predicate.TransactionPredicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TransactionService(
    val repository: TransactionRepository,
    val cardService: CardService,
    val accountService: AccountService
) {

    fun findAll(pageable: Pageable, filterRequest: TransactionFilterRequest): Page<TransactionDocument> =
        repository.findAll(TransactionPredicate.from(filterRequest), pageable)

    fun findById(id: String) =
        repository.findByIdOrNull(id) ?: throw NotFoundException("transaction.exception.notFound", id)

    fun transfer(transaction: TransactionDocument): TransactionDocument {
        when {
            transaction.isCard() -> cardService.transfer(transaction)
            else -> accountService.transfer(transaction)
        }

        return repository.save(transaction)
    }

    fun update(newTransaction: TransactionDocument) =
        findById(newTransaction.referenceId).let {
            if (it.value != newTransaction.value) {
                when {
                    newTransaction.isCard() -> cardService.updateTransaction(it, newTransaction)
                    else -> accountService.updateTransaction(it, newTransaction)
                }
            }
            repository.save(newTransaction)
        }

    fun delete(id: String) {
        findById(id).run {
            when {
                this.isCard() -> cardService.reversal(this)
                else -> accountService.reversal(this)
            }
            repository.deleteById(id)
        }
    }
}