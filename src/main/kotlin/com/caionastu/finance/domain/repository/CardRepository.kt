package com.caionastu.finance.domain.repository

import com.caionastu.finance.domain.entity.CardDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : MongoRepository<CardDocument, String> {
    fun existsByFinalNumberAndDeleted(finalNumber: String, deleted: Boolean = false): Boolean
    fun findByIdAndDeleted(id: String, deleted: Boolean = false): CardDocument?
}