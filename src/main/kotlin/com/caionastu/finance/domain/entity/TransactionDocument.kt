package com.caionastu.finance.domain.entity

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDate

data class TransactionDocument(
    @Id
    val id: String,
    val type: PaymentType,
    val value: BigDecimal,
    val date: LocalDate,
    val referenceId: String? = null // bank account id or card id
)

enum class PaymentType {
    CREDIT,
    INCOME,
    EXPENSE
}
