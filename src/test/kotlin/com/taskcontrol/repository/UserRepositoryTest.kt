package com.taskcontrol.repository

import com.taskcontrol.application.model.User
import com.taskcontrol.application.model.Role
import com.taskcontrol.repository.jpa.IUserJpaRepository
import com.taskcontrol.repository.jpa.entity.UserEntity
import com.taskcontrol.repository.jpa.mapper.UserMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class UserRepositoryTest {

    @Mock
    private lateinit var userJpaRepository: IUserJpaRepository

    @InjectMocks
    private lateinit var repository: UserRepository

    @Test
    fun `should save user`() {
        // Given
        val userId = UUID.randomUUID()
        val user = User(userId, "testuser", "password123", Role.USER)
        val userEntity = UserMapper.toEntity(user)

        `when`(userJpaRepository.save(any(UserEntity::class.java))).thenReturn(userEntity)

        // When
        val result = repository.save(user)

        // Then
        assertEquals(user, result)
        verify(userJpaRepository).save(userEntity)
    }

    @Test
    fun `should delete user by id`() {
        // Given
        val userId = UUID.randomUUID()
        doNothing().`when`(userJpaRepository).deleteById(userId)

        // When
        repository.deleteById(userId)

        // Then
        verify(userJpaRepository).deleteById(userId)
    }

    @Test
    fun `should find all users`() {
        // Given
        val users = listOf<UserEntity>()
        `when`(userJpaRepository.findAll()).thenReturn(users)

        // When
        val result = repository.findAll()

        // Then
        assertEquals(UserMapper.toModels(users), result)
        verify(userJpaRepository).findAll()
    }

    @Test
    fun `should find user by id`() {
        // Given
        val userId = UUID.randomUUID()
        val userEntity = UserEntity(id = userId, username = "testuser", password = "password123", role = Role.USER)
        `when`(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity))

        // When
        val result = repository.findById(userId)

        // Then
        assertEquals(UserMapper.toModel(userEntity), result)
        verify(userJpaRepository).findById(userId)
    }

    @Test
    fun `should find user by username`() {
        // Given
        val username = "testuser"
        val userEntity = UserEntity(id = UUID.randomUUID(), username = username, password = "password123", role = Role.USER)
        `when`(userJpaRepository.findByUsername(username)).thenReturn(userEntity)

        // When
        val result = repository.findByUsername(username)

        // Then
        assertEquals(UserMapper.toModel(userEntity), result)
        verify(userJpaRepository).findByUsername(username)
    }

    @Test
    fun `should change user role`() {
        // Given
        val userId = UUID.randomUUID()
        val newRole = Role.ADMIN
        val userEntity = UserEntity(id = userId, username = "testuser", password = "password123", role = Role.USER)

        `when`(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity))
        `when`(userJpaRepository.save(any(UserEntity::class.java))).thenReturn(userEntity.copy(role = newRole))

        // When
        repository.changeUserRole(userId, newRole)

        // Then
        assertEquals(newRole, userEntity.role)
        verify(userJpaRepository).findById(userId)
        verify(userJpaRepository).save(userEntity)
    }
}