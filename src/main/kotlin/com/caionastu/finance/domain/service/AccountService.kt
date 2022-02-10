package com.caionastu.finance.domain.service

import com.caionastu.finance.application.web.model.request.AccountFilterRequest
import com.caionastu.finance.domain.entity.AccountDocument
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

    fun findById(id: String) = repository.findByIdAndDeleted(id)
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
}