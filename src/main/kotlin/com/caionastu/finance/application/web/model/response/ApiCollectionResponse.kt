package com.caionastu.finance.application.web.model.response

import org.springframework.data.domain.Page


data class ApiCollectionResponse<T>(
    val hasNext: Boolean = false,
    val pageSize: Int = 20,
    val pageNumber: Int = 0,
    val totalElements: Int = 0,
    val items: Collection<T> = emptyList()
) {

    constructor(page: Page<T>) : this(
        page.hasNext(),
        page.size,
        page.number,
        page.totalElements.toInt(),
        page.content
    )

}