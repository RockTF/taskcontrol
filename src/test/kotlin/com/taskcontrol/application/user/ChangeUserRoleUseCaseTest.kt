package com.taskcontrol.application.user

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.port.IUserRepository
import com.taskcontrol.application.usecase.user.ChangeUserRoleUseCase
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.Mockito.doThrow
import org.mockito.junit.jupiter.MockitoExtension
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class ChangeUserRoleUseCaseTest {

    @Mock
    private lateinit var userRepository: IUserRepository

    @InjectMocks
    private lateinit var changeUserRoleUseCase: ChangeUserRoleUseCase

    @Test
    fun `should change user role`() {
        // Given
        val userId = UUID.randomUUID()
        val newRole = Role.ADMIN

        // Mock behavior
        doNothing().`when`(userRepository).changeUserRole(userId, newRole)

        // When
        assertDoesNotThrow { changeUserRoleUseCase.changeUserRole(userId, newRole) }

        // Then
        verify(userRepository).changeUserRole(userId, newRole)
    }

    @Test
    fun `should throw exception when user not found`() {
        // Given
        val userId = UUID.randomUUID()
        val newRole = Role.ADMIN
        val expectedExceptionMessage = "User not found with id: $userId"
        val exception = NoSuchElementException(expectedExceptionMessage)

        // Mock behavior
        doThrow(exception).`when`(userRepository).changeUserRole(userId, newRole)

        // When
        val actualException = assertThrows<NoSuchElementException> {
            changeUserRoleUseCase.changeUserRole(userId, newRole)
        }

        // Then
        assertEquals(expectedExceptionMessage, actualException.message)
        verify(userRepository).changeUserRole(userId, newRole)
    }
}