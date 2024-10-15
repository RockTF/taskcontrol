package com.taskcontrol.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("TaskControl API")
                    .version("v1")
                    .description("API документація для TaskControl")
                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("http://springdoc.org")
                    )
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("TaskControl Вікі")
                    .url("https://example.com/v1/wiki")
            )
    }

    @Bean
    fun adminApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("admin")
            .pathsToMatch("/admin/**")
            .build()
    }

    @Bean
    fun authApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("auth")
            .pathsToMatch("/authenticate")
            .build()
    }

    @Bean
    fun exportApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("export")
            .pathsToMatch("/admin/export/**")
            .build()
    }

    @Bean
    fun tasksApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("tasks")
            .pathsToMatch("/tasks/**")
            .build()
    }

    @Bean
    fun userApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("user")
            .pathsToMatch("/user/**")
            .build()
    }
}
