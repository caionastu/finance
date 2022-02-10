package com.caionastu.finance.application.web.controller

import com.caionastu.finance.application.web.model.request.AccountFilterRequest
import com.caionastu.finance.application.web.model.request.AccountRequest
import com.caionastu.finance.application.web.model.response.AccountResponse
import com.caionastu.finance.application.web.model.response.ApiCollectionResponse
import com.caionastu.finance.domain.service.AccountService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("v1/accounts")
class AccountController(
    private val service: AccountService
) {

    @GetMapping
    fun findAll(pageable: Pageable, filterRequest: AccountFilterRequest) = ApiCollectionResponse(
        service.findAll(pageable, filterRequest)
            .map {
                AccountResponse(it)
            }
    )

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) =
        AccountResponse(service.findById(id))

    @PostMapping
    fun create(@Valid @RequestBody request: AccountRequest) =
        AccountResponse(service.create(request.toDocument()))

    @PostMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody request: AccountRequest) =
        AccountResponse(service.update(request.toDocument(id)))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) =
        service.delete(id)

}