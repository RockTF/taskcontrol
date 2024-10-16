package com.taskcontrol.repository.jpa.mapper

import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import org.hamcrest.Matchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import com.taskcontrol.repository.jpa.entity.TaskEntity
import com.taskcontrol.repository.jpa.entity.UserEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.UUID

class TaskMapperTest {
    private val mapper = TaskMapper

    @Test
    fun `should map TaskEntity to Task`() {
        // Given
        val userId = UUID.randomUUID()
        val taskEntityId = UUID.randomUUID()
        val createDate = LocalDate.now()

        val userEntity = UserEntity(
            id = userId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        val taskEntity = TaskEntity(
            id = taskEntityId,
            user = userEntity,
            title = "Test Task",
            description = "This is a test task",
            deadline = createDate,
            priority = Priority.HIGH,
            status = Status.PENDING
        )

        val expected = Task(
            id = taskEntityId,
            userId = userId,
            title = "Test Task",
            description = "This is a test task",
            deadline = createDate,
            priority = Priority.HIGH,
            status = Status.PENDING
        )

        // When
        val actual = mapper.toModel(taskEntity)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `should map list of TaskEntities to list of Tasks`() {
        // Given
        val userId = UUID.randomUUID()
        val createDate = LocalDate.now()

        val userEntity = UserEntity(
            id = userId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        val taskEntityId1 = UUID.randomUUID()
        val taskEntityId2 = UUID.randomUUID()

        val taskEntity1 = TaskEntity(
            id = taskEntityId1,
            user = userEntity,
            title = "Test Task 1",
            description = "This is the first test task",
            deadline = createDate,
            priority = Priority.MEDIUM,
            status = Status.PENDING
        )

        val taskEntity2 = TaskEntity(
            id = taskEntityId2,
            user = userEntity,
            title = "Test Task 2",
            description = "This is the second test task",
            deadline = createDate,
            priority = Priority.LOW,
            status = Status.COMPLETED
        )

        val expected = listOf(
            Task(
                id = taskEntityId1,
                userId = userId,
                title = "Test Task 1",
                description = "This is the first test task",
                deadline = createDate,
                priority = Priority.MEDIUM,
                status = Status.PENDING
            ),
            Task(
                id = taskEntityId2,
                userId = userId,
                title = "Test Task 2",
                description = "This is the second test task",
                deadline = createDate,
                priority = Priority.LOW,
                status = Status.COMPLETED
            )
        )

        // When
        val actual = mapper.toModels(listOf(taskEntity1, taskEntity2))

        // Then
        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should map Task to TaskEntity`() {
        // Given
        val userId = UUID.randomUUID()
        val taskId = UUID.randomUUID()
        val createDate = LocalDate.now()

        val userEntity = UserEntity(
            id = userId,
            username = "testuser",
            password = "password123",
            role = Role.ADMIN
        )

        val task = Task(
            id = taskId,
            userId = userId,
            title = "Test Task",
            description = "This is a test task",
            deadline = createDate,
            priority = Priority.HIGH,
            status = Status.PENDING
        )

        val expected = TaskEntity(
            id = taskId,
            user = userEntity,
            title = "Test Task",
            description = "This is a test task",
            deadline = createDate,
            priority = Priority.HIGH,
            status = Status.PENDING
        )

        // When
        val actual = mapper.toEntity(task, userEntity)

        // Then
        assertEquals(expected, actual)
    }
}