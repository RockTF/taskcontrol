package com.taskcontrol.repository.jpa.mapper

import com.taskcontrol.application.model.User
import com.taskcontrol.application.model.Role
import org.hamcrest.Matchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import com.taskcontrol.repository.jpa.entity.UserEntity
import org.junit.jupiter.api.Test
import java.util.UUID

class UserMapperTest {
    private val mapper = UserMapper

    @Test
    fun `should map UserEntity to User`() {
        // Given
        val userId = UUID.randomUUID()
        val userEntity = UserEntity(
            id = userId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        val expected = User(
            id = userId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        // When
        val actual = mapper.toModel(userEntity)

        // Then
        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should map list of UserEntities to list of Users`() {
        // Given
        val userId1 = UUID.randomUUID()
        val userId2 = UUID.randomUUID()

        val userEntity1 = UserEntity(
            id = userId1,
            username = "testuser1",
            password = "password123",
            role = Role.USER
        )

        val userEntity2 = UserEntity(
            id = userId2,
            username = "testuser2",
            password = "password456",
            role = Role.ADMIN
        )

        val expected = listOf(
            User(
                id = userId1,
                username = "testuser1",
                password = "password123",
                role = Role.USER
            ),
            User(
                id = userId2,
                username = "testuser2",
                password = "password456",
                role = Role.ADMIN
            )
        )

        // When
        val actual = mapper.toModels(listOf(userEntity1, userEntity2))

        // Then
        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should map User to UserEntity`() {
        // Given
        val userId = UUID.randomUUID()

        val user = User(
            id = userId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        val expected = UserEntity(
            id = userId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        // When
        val actual = mapper.toEntity(user)

        // Then
        assertThat(actual, equalTo(expected))
    }
}