package com.taskcontrol.application.usecase.user.get

import com.taskcontrol.application.model.User
import com.taskcontrol.application.port.IUserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service

@Service
class GetCurrentUser(
    private val userRepository: IUserRepository
) : IGetCurrentUser {
    override fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        val username = authentication.token.subject

        return userRepository.findByUsername(username)
            ?: throw NoSuchElementException("User not found with username: $username")
    }
}
