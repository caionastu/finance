package com.caionastu.finance.application.web.model.response

import com.caionastu.finance.domain.entity.PaymentType
import com.caionastu.finance.domain.entity.TransactionDocument
import java.math.BigDecimal
import java.time.LocalDate

data class TransactionResponse(
    val id: String,
    val date: LocalDate,
    val value: BigDecimal,
    val category: String,
    val type: PaymentType,
    val referenceId: String,
    val description: String? = null,
) {
    constructor(transaction: TransactionDocument) : this(
        id = transaction.id,
        date = transaction.date,
        value = transaction.value,
        category = transaction.category,
        type = transaction.type,
        referenceId = transaction.referenceId,
        description = transaction.description
    )
}