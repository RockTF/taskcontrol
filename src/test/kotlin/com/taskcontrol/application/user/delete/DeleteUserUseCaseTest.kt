package com.taskcontrol.application.user.delete

import com.taskcontrol.application.port.IUserRepository
import com.taskcontrol.application.usecase.user.delete.DeleteUserUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class DeleteUserUseCaseTest {

    @Mock
    private lateinit var userRepository: IUserRepository

    @InjectMocks
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Test
    fun `should delete user by id`() {
        // Given
        val userId = UUID.randomUUID()

        doNothing().`when`(userRepository).deleteById(userId)

        // When
        deleteUserUseCase.deleteUser(userId)

        // Then
        verify(userRepository).deleteById(userId)
    }
}