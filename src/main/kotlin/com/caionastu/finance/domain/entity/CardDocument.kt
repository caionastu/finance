package com.caionastu.finance.domain.entity

import org.springframework.data.annotation.Id
import java.math.BigDecimal

data class CardDocument(
    @Id
    val id: String,
    val finalNumber: String,
    val name: String,
    val maxLimit: BigDecimal,
    val currentLimit: BigDecimal = BigDecimal.ZERO,
    val deleted: Boolean = false
) {
    fun hasLimit(amountToTransfer: BigDecimal) = currentLimit.plus(amountToTransfer) <= maxLimit
}