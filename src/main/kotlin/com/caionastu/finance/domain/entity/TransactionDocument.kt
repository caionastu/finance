package com.caionastu.finance.domain.entity

import com.querydsl.core.annotations.QueryEntity
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDate

@QueryEntity
data class TransactionDocument(
    @Id
    val id: String,
    val date: LocalDate,
    val value: BigDecimal,
    val category: String,
    val type: PaymentType,
    val referenceId: String,
    val description: String? = null,
) {
    fun isCard() = when (type) {
        PaymentType.CARD -> true
        else -> false
    }
}

enum class PaymentType {
    CARD,
    INCOME,
    EXPENSE
}
