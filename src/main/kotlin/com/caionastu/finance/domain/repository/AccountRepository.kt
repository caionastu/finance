package com.caionastu.finance.domain.repository

import com.caionastu.finance.domain.entity.AccountDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : MongoRepository<AccountDocument, String> {
    fun existsByNumber(number: String): Boolean
    fun existsByNumberAndIdNot(number: String, id: String): Boolean
    fun findAllByDeleted(pageable: Pageable, deleted: Boolean): Page<AccountDocument>
    fun findByIdAndDeleted(id: String, deleted: Boolean = false): AccountDocument?
}