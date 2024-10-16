package com.taskcontrol.application.user.get

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.User
import com.taskcontrol.application.port.IUserRepository
import com.taskcontrol.application.usecase.user.get.GetUserUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class GetUserUseCaseTest {

    @Mock
    private lateinit var userRepository: IUserRepository

    @InjectMocks
    private lateinit var getUserUseCase: GetUserUseCase

    @Test
    fun `should return all users`() {
        // Given
        val user1 = User(UUID.randomUUID(), "user1", "password1", Role.USER)
        val user2 = User(UUID.randomUUID(), "user2", "password2", Role.ADMIN)
        val users = listOf(user1, user2)

        `when`(userRepository.findAll()).thenReturn(users)

        // When
        val result = getUserUseCase.getAllUsers()

        // Then
        assertEquals(users, result)
        verify(userRepository).findAll()
    }
}