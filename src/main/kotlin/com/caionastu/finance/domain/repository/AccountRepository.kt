package com.caionastu.finance.domain.repository

import com.caionastu.finance.domain.entity.AccountDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : MongoRepository<AccountDocument, String> {
}