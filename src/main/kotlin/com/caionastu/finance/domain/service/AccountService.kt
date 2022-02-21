package com.caionastu.finance.domain.service

import com.caionastu.finance.application.web.model.request.AccountFilterRequest
import com.caionastu.finance.domain.entity.AccountDocument
import com.caionastu.finance.domain.entity.PaymentType
import com.caionastu.finance.domain.entity.TransactionDocument
import com.caionastu.finance.domain.exception.BusinessException
import com.caionastu.finance.domain.exception.NotFoundException
import com.caionastu.finance.domain.repository.AccountRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val repository: AccountRepository
) {

    fun findAll(pageable: Pageable, filterRequest: AccountFilterRequest) =
        repository.findAllByDeleted(pageable, filterRequest.deleted)

    fun findById(id: String, deleted: Boolean = false) = repository.findByIdAndDeleted(id, deleted)
        ?: throw NotFoundException("account.exception.notFound", id)

    fun create(account: AccountDocument) =
        when (repository.existsByNumber(account.number)) {
            true -> throw BusinessException(
                "account.exception.exists.byNumber",
                HttpStatus.BAD_REQUEST,
                account.number
            )
            else -> repository.save(account)
        }

    fun update(account: AccountDocument) =
        findById(account.id).let {
            if (repository.existsByNumberAndIdNot(account.number, account.id))
                throw BusinessException(
                    "account.exception.exists.byNumber",
                    HttpStatus.BAD_REQUEST,
                    account.number
                )
            repository.save(account)
        }

    fun delete(id: String) {
        findById(id).run {
            repository.save(this.copy(deleted = true))
        }
    }

    fun transfer(transaction: TransactionDocument) {
        findById(transaction.referenceId).run {
            transferBalanceTo(this, transaction).apply {
                repository.save(this)
            }
        }
    }

    fun reversal(transaction: TransactionDocument) {
        findById(transaction.referenceId).run {
            revertBalanceFrom(this, transaction).apply {
                repository.save(this)
            }
        }
    }

    private fun transferBalanceTo(account: AccountDocument, transaction: TransactionDocument) =
        when (transaction.type) {
            PaymentType.EXPENSE -> {
                if (!account.hasBalance(transaction.value))
                    throw BusinessException("account.exception.balance.insufficient")

                subtract(account, transaction)
            }
            PaymentType.INCOME -> add(account, transaction)
            else -> throw BusinessException(
                "account.exception.paymentType.invalid",
                HttpStatus.BAD_REQUEST,
                transaction.type
            )
        }

    private fun revertBalanceFrom(account: AccountDocument, transaction: TransactionDocument) =
        when (transaction.type) {
            PaymentType.EXPENSE -> add(account, transaction)
            PaymentType.INCOME -> subtract(account, transaction)
            else -> throw BusinessException(
                "account.exception.paymentType.invalid",
                HttpStatus.BAD_REQUEST,
                transaction.type
            )
        }


    fun updateTransaction(oldTransaction: TransactionDocument, newTransaction: TransactionDocument) {
        findById(oldTransaction.referenceId).run {
            transferBalanceTo(
                revertBalanceFrom(this, oldTransaction),
                newTransaction
            ).apply {
                repository.save(this)
            }
        }
    }

    private fun add(account: AccountDocument, transaction: TransactionDocument) =
        account.copy(balance = account.balance.plus(transaction.value))

    private fun subtract(account: AccountDocument, transaction: TransactionDocument) =
        account.copy(balance = account.balance.minus(transaction.value))

}
