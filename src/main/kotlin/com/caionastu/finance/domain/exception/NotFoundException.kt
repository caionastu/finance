package com.caionastu.finance.domain.exception

import org.springframework.http.HttpStatus

class NotFoundException : BusinessException {

    constructor(keyMessage: String) : super(keyMessage, HttpStatus.NOT_FOUND)

    constructor(keyMessage: String, vararg arguments: String?) : super(keyMessage, HttpStatus.NOT_FOUND, *arguments)
}