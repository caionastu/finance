package com.caionastu.finance.application.web.advice

import com.caionastu.finance.application.web.advice.model.ErrorMessage
import com.caionastu.finance.domain.exception.BusinessException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*
import kotlin.reflect.full.valueParameters

@RestControllerAdvice
class ExceptionAdvice(val messageSource: MessageSource) {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationError(
        exception: MethodArgumentNotValidException,
        locale: Locale
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(messageSource.getMessage("badRequest.exception.title", null, locale))
        for (error: ObjectError in exception.bindingResult.allErrors) {
            if (error is FieldError) {
                val message = messageSource.getMessage(error.code ?: "", error.arguments, error.defaultMessage, locale)
                errorMessage.addDetail(message!!)
            } else {
                errorMessage.addDetail(messageSource.getMessage(error.code ?: "", error.arguments, locale))
            }

        }
        return ResponseEntity.badRequest().body(errorMessage)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(exception: BusinessException, locale: Locale) =
        handleExceptionMessage(exception, locale)

    private fun handleExceptionMessage(
        exception: BusinessException,
        locale: Locale
    ): ResponseEntity<ErrorMessage> =
        when (exception.status) {
            HttpStatus.BAD_REQUEST -> "badRequest.exception.title"
            HttpStatus.NOT_FOUND -> "notFound.exception.title"
            HttpStatus.INTERNAL_SERVER_ERROR -> "internalServerError.exception.title"
            else -> "generic.exception.title"
        }.let { errorTitle ->
            ErrorMessage(messageSource.getMessage(errorTitle, null, locale)).also {
                it.addDetail(
                    messageSource.getMessage(
                        exception.keyMessage,
                        exception.arguments,
                        locale
                    )
                )
            }.let { errorMessage ->
                ResponseEntity.status(exception.status).body(errorMessage)
            }
        }


}