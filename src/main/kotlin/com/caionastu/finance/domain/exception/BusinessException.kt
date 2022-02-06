package com.caionastu.finance.domain.exception

import org.springframework.http.HttpStatus


open class BusinessException : RuntimeException {
    val status: HttpStatus
    val keyMessage: String
    var arguments: Array<Any?> = emptyArray()

    constructor(keyMessage: String, status: HttpStatus = HttpStatus.BAD_REQUEST) {
        this.keyMessage = keyMessage
        this.status = status
    }

    constructor(keyMessage: String, status: HttpStatus = HttpStatus.BAD_REQUEST, vararg arguments: Any?) {
        this.keyMessage = keyMessage
        this.arguments = arrayOf(*arguments)
        this.status = status
    }

}