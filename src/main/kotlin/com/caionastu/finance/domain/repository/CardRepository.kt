package com.caionastu.finance.domain.repository

import com.caionastu.finance.domain.entity.CardDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : MongoRepository<CardDocument, String> {
    fun existsByFinalNumberAndDeleted(finalNumber: String, deleted: Boolean = false): Boolean
    fun findByIdAndDeleted(id: String, deleted: Boolean = false): CardDocument?
    fun findAllByDeleted(pageable: Pageable, deleted: Boolean): Page<CardDocument>
}