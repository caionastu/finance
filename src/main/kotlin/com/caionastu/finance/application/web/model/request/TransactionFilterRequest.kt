package com.caionastu.finance.application.web.model.request

import com.caionastu.finance.domain.entity.PaymentType
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class TransactionFilterRequest(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val startDate: LocalDate?,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val endDate: LocalDate?,
    val referenceId: String?,
    val category: String?,
    val type: PaymentType?
)
