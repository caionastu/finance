package com.caionastu.finance.application.web.model.response

import com.caionastu.finance.domain.entity.AccountDocument
import java.math.BigDecimal

data class AccountResponse(
    val id: String,
    val number: String,
    val digit: String,
    val balance: BigDecimal,
    val bankCode: String?,
    val name: String?,
    val deleted: Boolean
) {
    constructor(account: AccountDocument) : this(
        id = account.id,
        number = account.number,
        digit = account.digit,
        balance = account.balance,
        bankCode = account.bankCode,
        name = account.name,
        deleted = account.deleted
    )
}