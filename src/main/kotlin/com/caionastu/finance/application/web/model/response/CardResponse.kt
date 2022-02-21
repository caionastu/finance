package com.caionastu.finance.application.web.model.response

import com.caionastu.finance.domain.entity.CardDocument
import java.math.BigDecimal

data class CardResponse(
    val id: String,
    val finalNumber: String,
    val name: String,
    val maxLimit: BigDecimal,
    val currentLimit: BigDecimal,
    val deleted: Boolean
) {
    constructor(card: CardDocument) : this(
        id = card.id,
        finalNumber = card.finalNumber,
        name = card.name,
        maxLimit = card.maxLimit,
        currentLimit = card.currentLimit,
        deleted = card.deleted
    )
}
