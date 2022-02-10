package com.caionastu.finance.application.web.controller

import com.caionastu.finance.application.web.model.request.CardFilterRequest
import com.caionastu.finance.application.web.model.request.CardRequest
import com.caionastu.finance.application.web.model.response.ApiCollectionResponse
import com.caionastu.finance.application.web.model.response.CardResponse
import com.caionastu.finance.domain.service.CardService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("v1/cards")
class CardController(
    private val service: CardService
) {

    @GetMapping
    fun findAll(pageable: Pageable, filterRequest: CardFilterRequest) = ApiCollectionResponse(
        service.findAll(pageable, filterRequest)
            .map { cardDocument -> CardResponse(cardDocument) }
    )

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) =
        CardResponse(service.findById(id))

    @PostMapping
    fun create(@Valid @RequestBody cardRequest: CardRequest) =
        CardResponse(service.create(cardRequest.toDocument()))

    @PostMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody cardRequest: CardRequest) =
        CardResponse(service.update(cardRequest.toDocument(id)))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = service.delete(id)
}