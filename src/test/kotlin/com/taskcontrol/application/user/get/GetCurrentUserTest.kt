package com.taskcontrol.application.user.get

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.User
import com.taskcontrol.application.port.IUserRepository
import com.taskcontrol.application.usecase.user.get.GetCurrentUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class GetCurrentUserTest {

    @Mock
    private lateinit var userRepository: IUserRepository

    @Mock
    private lateinit var securityContext: SecurityContext

    @Mock
    private lateinit var authenticationToken: JwtAuthenticationToken

    @InjectMocks
    private lateinit var getCurrentUser: GetCurrentUser

    @Mock
    private lateinit var jwt: Jwt

    @Test
    fun `should return current user`() {
        // Given
        val username = "testuser"
        val user = User(UUID.randomUUID(), username, "password123", Role.USER)

        `when`(jwt.subject).thenReturn(username)
        `when`(authenticationToken.token).thenReturn(jwt)
        `when`(securityContext.authentication).thenReturn(authenticationToken)
        `when`(userRepository.findByUsername(username)).thenReturn(user)
        SecurityContextHolder.setContext(securityContext)

        // When
        val result = getCurrentUser.getCurrentUser()

        // Then
        assertEquals(user, result)
        verify(userRepository).findByUsername(username)
    }

    @Test
    fun `should throw exception when user not found`() {
        // Given
        val username = "testuser"

        `when`(jwt.subject).thenReturn(username)
        `when`(authenticationToken.token).thenReturn(jwt)
        `when`(securityContext.authentication).thenReturn(authenticationToken)
        `when`(userRepository.findByUsername(username)).thenReturn(null)
        SecurityContextHolder.setContext(securityContext)

        // When / Then
        val exception = assertThrows(NoSuchElementException::class.java) {
            getCurrentUser.getCurrentUser()
        }

        assertEquals("User not found with username: $username", exception.message)
        verify(userRepository).findByUsername(username)
    }
}