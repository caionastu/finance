package com.caionastu.finance.domain.entity

import org.springframework.data.annotation.Id
import java.math.BigDecimal

data class AccountDocument(
    @Id
    val id: String,
    val number: String,
    val digit: String,
    val bankCode: String,
    val balance: BigDecimal
)