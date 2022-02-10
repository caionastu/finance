package com.caionastu.finance.application.web.model.request

import com.caionastu.finance.domain.entity.AccountDocument
import org.bson.types.ObjectId
import java.math.BigDecimal
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class AccountRequest(
    @get:Pattern(regexp = "\\d{4,11}")
    val number: String,
    @get:Pattern(regexp = "\\d{1,2}")
    val digit: String,
    @get:Positive
    val balance: BigDecimal,
    @get:Pattern(regexp = "\\d{3}")
    val bankCode: String? = null,
    @get:Size(min = 0, max = 32)
    val name: String? = null
) {
    fun toDocument(id: String? = null) =
        AccountDocument(
            id = id ?: ObjectId().toString(),
            number = number,
            digit = digit,
            bankCode = bankCode,
            balance = balance,
            name = name,
        )
}
