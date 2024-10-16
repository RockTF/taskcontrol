package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.UserDtoMapper
import com.taskcontrol.application.model.AuthenticationRequest
import com.taskcontrol.application.model.AuthenticationResponse
import com.taskcontrol.application.usecase.authentication.JwtUtil
import com.taskcontrol.application.usecase.authentication.MyUserDetailsService
import com.taskcontrol.application.usecase.user.create.ICreateUserUseCase
import com.taskcontrol.domain.UserDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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

    @Operation(summary = "Create authentication token", description = "Authenticates the user and returns a JWT token.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Authenticated successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        val authenticationToken = UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
        authenticationManager.authenticate(authenticationToken)

        val userDetails: UserDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt: String = jwtUtil.generateToken(userDetails)

        return AuthenticationResponse(jwt)
    }

    @Operation(summary = "Register a new user", description = "Registers a new user and returns the created user.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User registered successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "400", description = "Invalid input")
    ])
    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserDto): UserDto {
        val userModel = UserDtoMapper.toModel(user).copy(
            password = passwordEncoder.encode(user.password)
        )
        return UserDtoMapper.toDto(createUserUseCase.registerUser(userModel))
    }
}