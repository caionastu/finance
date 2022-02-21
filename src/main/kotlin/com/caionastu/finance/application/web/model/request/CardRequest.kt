package com.caionastu.finance.application.web.model.request

import com.caionastu.finance.domain.entity.CardDocument
import org.bson.types.ObjectId
import java.math.BigDecimal
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

data class CardRequest(
    @get:Pattern(regexp = "\\d{4}", message ="{card.finalNumber.invalid}")
    val finalNumber: String,
    val name: String,
    @get:Positive(message = "{card.limit.positive}")
    val maxLimit: BigDecimal,
) {
    fun toDocument(id: String? = null) = CardDocument(
        id = id ?: ObjectId().toString(),
        finalNumber = finalNumber,
        name = name,
        maxLimit = maxLimit
    )
}