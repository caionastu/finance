package com.caionastu.finance.application.web.controller

import com.caionastu.finance.application.web.model.request.TransactionFilterRequest
import com.caionastu.finance.application.web.model.request.TransactionRequest
import com.caionastu.finance.application.web.model.response.ApiCollectionResponse
import com.caionastu.finance.application.web.model.response.TransactionResponse
import com.caionastu.finance.domain.service.TransactionService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/transactions")
class TransactionController(
    private val service: TransactionService
) {

    @GetMapping
    fun findAll(
        pageable: Pageable,
        filterRequest: TransactionFilterRequest
    ) =
        ApiCollectionResponse(service.findAll(pageable, filterRequest))

    @PostMapping
    fun transfer(@Valid @RequestBody request: TransactionRequest) =
        TransactionResponse(service.transfer(request.toDocument()))

    @PostMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody request: TransactionRequest) =
        TransactionResponse(service.update(request.toDocument(id)))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) {
        service.delete(id)
    }

}