package com.caionastu.finance.application.web.model.request

import com.caionastu.finance.domain.entity.PaymentType
import com.caionastu.finance.domain.entity.TransactionDocument
import org.bson.types.ObjectId
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class TransactionRequest(
    @get:NotNull
    val date: LocalDate,
    @get:Positive
    val value: BigDecimal,
    val category: String = "Restaurante",
    @get:NotNull
    val type: PaymentType,
    @get:NotBlank
    val referenceId: String,
    val description: String? = null,
) {
    fun toDocument(id: String? = null) =
        TransactionDocument(
            id = id ?: ObjectId().toString(),
            date = date,
            value = value,
            category = category,
            type = type,
            referenceId = referenceId,
            description = description,
        )
}