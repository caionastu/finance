package com.caionastu.finance.application.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class MessagePropertiesConfiguration {

    @Bean
    fun messageSource() = ResourceBundleMessageSource().also {
        it.setBasename("messages/messages")
        it.setDefaultEncoding("UTF-8")
        it.setUseCodeAsDefaultMessage(true)
    }

    @Bean
    fun getValidatorFactoryBean() = LocalValidatorFactoryBean().also {
        it.setValidationMessageSource(messageSource())
    }
    
}