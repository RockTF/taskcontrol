package com.taskcontrol.config

import com.taskcontrol.application.port.IUserRepository
import com.taskcontrol.application.usecase.authentication.JwtUtil
import com.taskcontrol.application.usecase.authentication.MyUserDetailsService
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@TestConfiguration
@EnableWebSecurity
@Import(JwtConfig::class)
class TestSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests.anyRequest().permitAll()
            }
        return http.build()
    }

    @Bean
    fun jwtUtil(jwtConfig: JwtConfig): JwtUtil {
        return JwtUtil(jwtConfig)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtDecoder(jwtConfig: JwtConfig): JwtDecoder {
        val secretKey: SecretKey = SecretKeySpec(jwtConfig.secret.toByteArray(), "HmacSHA256")
        return NimbusJwtDecoder.withSecretKey(secretKey).build()
    }

    @Bean
    fun myUserDetailsService(userRepository: IUserRepository): MyUserDetailsService {
        return MyUserDetailsService(userRepository)
    }

    @Bean
    fun mockedUserRepository(): IUserRepository {
        return Mockito.mock(IUserRepository::class.java)
    }
}