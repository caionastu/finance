package com.caionastu.finance.application.web.advice.model

class ErrorMessage(val title: String) {
    val details: ArrayList<String> = arrayListOf()

    fun addDetail(detail: String) {
        details.add(detail)
    }
}