package com.caionastu.finance.application.web.model.response

import com.caionastu.finance.domain.entity.CardDocument
import com.caionastu.finance.domain.entity.CardType
import java.math.BigDecimal

data class CardResponse(
    val id: String,
    val finalNumber: String,
    val type: CardType,
    val name: String,
    val creditLimit: BigDecimal?,
    val debitAccountId: String? = null,
    val deleted: Boolean
) {
    constructor(card: CardDocument) : this(
        id = card.id,
        finalNumber = card.finalNumber,
        type = card.type,
        name = card.name,
        creditLimit = card.creditLimit,
        debitAccountId = card.debitAccountId,
        deleted = card.deleted
    )
}
