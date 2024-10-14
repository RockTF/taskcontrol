package com.taskcontrol.config

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: MyUserDetailsService,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): AuthenticationResponse {

        val authenticationToken = UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
        authenticationManager.authenticate(authenticationToken)

        val userDetails: UserDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)

        val jwt: String = jwtUtil.generateToken(userDetails)

        return AuthenticationResponse(jwt)
    }
}

data class AuthenticationRequest(val username: String, val password: String)
data class AuthenticationResponse(val jwt: String)