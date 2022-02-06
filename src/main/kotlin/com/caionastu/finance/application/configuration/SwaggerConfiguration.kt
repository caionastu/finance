package com.caionastu.finance.application.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun swaggerDocket(): OpenAPI = OpenAPI()
        .info(
            Info().title("Financial API")
                .description("An API to control your finances.")
                .version("v0.0.1")
        )
}