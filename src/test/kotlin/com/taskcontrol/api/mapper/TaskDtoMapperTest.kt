package com.taskcontrol.api.mapper

import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.domain.TaskDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.UUID

internal class TaskDtoMapperTest {
    private val mapper = TaskDtoMapper

    @Test
    fun `should map Task to TaskDto`() {
        // Given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val createDate = LocalDate.now()
        val task = Task(
            id = taskId,
            userId = userId,
            title = "Test Task",
            description = "This is a test task",
            deadline = createDate,
            priority = Priority.HIGH,
            status = Status.PENDING
        )

        val expected = TaskDto(
            id = taskId,
            userId = userId,
            title = task.title,
            description = task.description,
            deadline = createDate,
            priority = "HIGH",
            status = "PENDING"
        )

        // When
        val actual = mapper.toDto(task)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `should map TaskDto to Task`() {
        // Given
        val taskDtoId = UUID.randomUUID()
        val taskDtoUserId = UUID.randomUUID()
        val createDate = LocalDate.now()
        val taskDto = TaskDto(
            id = taskDtoId,
            userId = taskDtoUserId,
            title = "Test Task DTO",
            description = "This is a test task DTO",
            deadline = createDate,
            priority = "HIGH",
            status = "PENDING"
        )

        val expected = Task(
            id = taskDtoId,
            userId = taskDtoUserId,
            title = taskDto.title,
            description = taskDto.description,
            deadline = createDate,
            priority = Priority.HIGH,
            status = Status.PENDING
        )

        // When
        val actual = mapper.toModel(taskDto)

        // Then
        assertEquals(expected, actual)
    }
}