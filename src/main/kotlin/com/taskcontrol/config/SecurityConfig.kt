package com.taskcontrol.config

import com.taskcontrol.application.usecase.authentication.JwtRequestFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Lazy private val jwtRequestFilter: JwtRequestFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/user/**").permitAll() // Дозволити доступ до /user/**
                    .requestMatchers("/task").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/task/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/auth/**").permitAll() // Дозволити доступ до /auth/**
                    .requestMatchers("/authenticate").permitAll() // ensuring /authenticate endpoint is opened
                    .requestMatchers("/register").permitAll() // ensuring /register endpoint is opened
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                    ).permitAll()
                    .anyRequest().authenticated()
            }

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secretKey: SecretKey = SecretKeySpec(
            "my-256-bit-secret-key-which-is-long-enough-5151".toByteArray(),
            "HmacSHA256"
        )
        return NimbusJwtDecoder.withSecretKey(secretKey).build()
    }
}