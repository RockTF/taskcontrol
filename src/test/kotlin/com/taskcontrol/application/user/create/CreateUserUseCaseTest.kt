package com.taskcontrol.application.user.create

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.User
import com.taskcontrol.application.port.IUserRepository
import com.taskcontrol.application.usecase.user.create.CreateUserUseCase
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
internal class CreateUserUseCaseTest {

    @Mock
    private lateinit var userRepository: IUserRepository

    @InjectMocks
    private lateinit var createUserUseCase: CreateUserUseCase

    @Test
    fun `should register and return new user`() {
        // Given
        val user = User(UUID.randomUUID(), "user1", "password1", Role.USER)

        `when`(userRepository.save(user)).thenReturn(user)

        // When
        val result = createUserUseCase.registerUser(user)

        // Then
        assertEquals(user, result)
        verify(userRepository).save(user)
    }
}