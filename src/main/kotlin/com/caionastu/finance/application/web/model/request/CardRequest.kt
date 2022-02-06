package com.caionastu.finance.application.web.model.request

import com.caionastu.finance.domain.entity.CardDocument
import com.caionastu.finance.domain.entity.CardType
import org.bson.types.ObjectId
import java.math.BigDecimal
import javax.validation.constraints.Pattern

data class CardRequest(
    @get:Pattern(regexp = "\\d{4}", message ="{card.finalNumber.invalid}")
    val finalNumber: String,
    val type: CardType,
    val name: String,
    val creditLimit: BigDecimal? = null,
    val debitAccountId: String? = null
) {
    fun toDocument(id: String? = null) = CardDocument(
        id = id ?: ObjectId().toString(),
        finalNumber = finalNumber,
        type = type,
        name = name,
        creditLimit = creditLimit,
        debitAccountId = debitAccountId
    )
}