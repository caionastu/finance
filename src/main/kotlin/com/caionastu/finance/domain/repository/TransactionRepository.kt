package com.caionastu.finance.domain.repository

import com.caionastu.finance.domain.entity.TransactionDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository :
    MongoRepository<TransactionDocument, String>,
    QuerydslPredicateExecutor<TransactionDocument> {
}