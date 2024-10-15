package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.UserDtoMapper
import com.taskcontrol.application.model.AuthenticationRequest
import com.taskcontrol.application.model.AuthenticationResponse
import com.taskcontrol.application.usecase.authentication.JwtUtil
import com.taskcontrol.application.usecase.authentication.MyUserDetailsService
import com.taskcontrol.application.usecase.user.create.ICreateUserUseCase
import com.taskcontrol.domain.UserDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "Authentication API")
@RestController
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: MyUserDetailsService,
    private val jwtUtil: JwtUtil,
    private val createUserUseCase: ICreateUserUseCase,
    private val passwordEncoder: PasswordEncoder

) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        val authenticationToken = UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
        authenticationManager.authenticate(authenticationToken)

        val userDetails: UserDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt: String = jwtUtil.generateToken(userDetails)

        return AuthenticationResponse(jwt)
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserDto): UserDto {
        val userModel = UserDtoMapper.toModel(user).copy(
            password = passwordEncoder.encode(user.password)
        )
        return UserDtoMapper.toDto(createUserUseCase.registerUser(userModel))
    }
}
