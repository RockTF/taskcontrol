package com.taskcontrol.api.mapper

import com.taskcontrol.application.model.User
import com.taskcontrol.application.model.Role
import com.taskcontrol.domain.UserDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class UserDtoMapperTest {
    private val mapper = UserDtoMapper

    @Test
    fun `should map User to UserDto`() {
        // Given
        val userId = UUID.randomUUID()
        val user = User(
            id = userId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        val expected = UserDto(
            id = userId,
            username = "testuser",
            password = "password123",
            role = "ADMIN"
        )

        // When
        val actual = mapper.toDto(user)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `should map UserDto to User`() {
        // Given
        val userDtoId = UUID.randomUUID()
        val userDto = UserDto(
            id = userDtoId,
            username = "testuser",
            password = "password123",
            role = "ADMIN"
        )

        val expected = User(
            id = userDtoId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        // When
        val actual = mapper.toModel(userDto)

        // Then
        assertEquals(expected, actual)
    }
}