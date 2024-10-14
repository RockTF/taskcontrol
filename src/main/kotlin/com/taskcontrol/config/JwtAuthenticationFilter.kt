package com.taskcontrol.config

import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.security.SignatureException
import java.util.Date

class JwtAuthenticationFilter : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")?.substring("Bearer ".length)
        if (token != null && validateToken(token)) {
            val userDetails = getUserDetailsFromToken(token)
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun validateToken(token: String): Boolean {
        try {
            val secretKey = "YourSecretKey"

            Jwts.parserBuilder()
                .setSigningKey(secretKey.toByteArray())
                .build()
                .parseClaimsJws(token)
                .body.apply {
                    return !isTokenExpired(this.expiration)
                }
        } catch (e: SignatureException) {
            logger.error("JWT signature validation failed: ${e.message}", e)
            return false
        } catch (e: Exception) {
            logger.error("Error parsing JWT: ${e.message}", e)
            return false
        }
    }

    private fun isTokenExpired(expiration: Date?): Boolean {
        return expiration?.before(Date()) ?: true
    }

    private fun getUserDetailsFromToken(token: String): UserDetails {
        val claims = Jwts.parserBuilder()
            .setSigningKey("YourSecretKey".toByteArray())
            .build()
            .parseClaimsJws(token)
            .body

        val username = claims.subject
        val roles = claims["roles"] as List<String>? ?: listOf()
        val authorities = roles.map { SimpleGrantedAuthority(it) }

        return User(username, "", authorities)
    }
}
