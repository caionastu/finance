package com.caionastu.finance.application.web.model.response

import com.caionastu.finance.domain.entity.CardDocument
import com.caionastu.finance.domain.entity.CardType

data class CardResponse(
    val id: String,
    val finalNumber: String,
    val type: CardType,
    val debitAccountId: String? = null
) {
    constructor(card: CardDocument) : this(
        id = card.id,
        finalNumber = card.finalNumber,
        type = card.type,
        debitAccountId = card.debitAccountId
    )
}
