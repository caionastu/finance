package com.caionastu.finance.domain.entity

import org.springframework.data.annotation.Id
import java.math.BigDecimal

data class CardDocument(
    @Id
    val id: String,
    val finalNumber: String,
    val type: CardType,
    val name: String,
    val creditLimit: BigDecimal? = null,
    val debitAccountId: String? = null,
    val deleted: Boolean = false
) {
    fun isDebit() = type == CardType.DEBIT
}

enum class CardType {
    CREDIT,
    DEBIT
}