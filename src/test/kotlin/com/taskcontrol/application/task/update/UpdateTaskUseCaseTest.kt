package com.taskcontrol.application.task.update

import com.taskcontrol.application.model.*
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.task.update.UpdateTaskUseCase
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class UpdateTaskUseCaseTest {

    @Mock
    private lateinit var taskRepository: ITaskRepository

    @Mock
    private lateinit var getCurrentUser: IGetCurrentUser

    @InjectMocks
    private lateinit var updateTaskUseCase: UpdateTaskUseCase

    private fun createTask(taskId: UUID, userId: UUID) = Task(
        id = taskId,
        userId = userId,
        title = "Sample Task",
        description = "Task Description",
        deadline = LocalDate.now().plusDays(1),
        priority = Priority.MEDIUM,
        status = Status.NOT_STARTED
    )

    @Test
    fun `should update task if user is admin`() {
        // Given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val existingTask = createTask(taskId, userId)
        val updatedTask = existingTask.copy(title = "Updated Task", status = Status.COMPLETED)
        val admin = User(UUID.randomUUID(), "admin", "password", Role.ADMIN)

        `when`(taskRepository.findById(taskId)).thenReturn(existingTask)
        `when`(getCurrentUser.getCurrentUser()).thenReturn(admin)
        `when`(taskRepository.save(updatedTask)).thenReturn(updatedTask)

        // When
        val result = updateTaskUseCase.updateTask(updatedTask)

        // Then
        assertEquals(updatedTask.title, result.title)
        assertEquals(updatedTask.status, result.status)
        verify(taskRepository).findById(taskId)
        verify(taskRepository).save(updatedTask)
    }

    @Test
    fun `should update task if user is owner`() {
        // Given
        val userId = UUID.randomUUID()
        val taskId = UUID.randomUUID()
        val existingTask = createTask(taskId, userId)
        val updatedTask = existingTask.copy(title = "Updated Task", status = Status.PENDING)
        val user = User(userId, "user", "password", Role.USER)

        `when`(taskRepository.findById(taskId)).thenReturn(existingTask)
        `when`(getCurrentUser.getCurrentUser()).thenReturn(user)
        `when`(taskRepository.save(updatedTask)).thenReturn(updatedTask)

        // When
        val result = updateTaskUseCase.updateTask(updatedTask)

        // Then
        assertEquals(updatedTask.title, result.title)
        assertEquals(updatedTask.status, result.status)
        verify(taskRepository).findById(taskId)
        verify(taskRepository).save(updatedTask)
    }

    @Test
    fun `should throw exception if user is neither admin nor owner`() {
        // Given
        val ownerUserId = UUID.randomUUID()
        val taskId = UUID.randomUUID()
        val existingTask = createTask(taskId, ownerUserId)
        val updatedTask = existingTask.copy(title = "Updated Task", status = Status.CANCELLED)
        val user = User(UUID.randomUUID(), "user", "password", Role.USER)

        `when`(taskRepository.findById(taskId)).thenReturn(existingTask)
        `when`(getCurrentUser.getCurrentUser()).thenReturn(user)

        // When / Then
        val exception = assertThrows(IllegalAccessException::class.java) {
            updateTaskUseCase.updateTask(updatedTask)
        }

        assertEquals("User is not allowed to update this task", exception.message)
        verify(taskRepository).findById(taskId)
    }

    @Test
    fun `should throw exception if task is not found`() {
        // Given
        val taskId = UUID.randomUUID()
        val user = User(UUID.randomUUID(), "user", "password", Role.USER)
        val updatedTask = createTask(taskId, user.id!!).copy(status = Status.PENDING)

        `when`(taskRepository.findById(taskId)).thenReturn(null)

        // When / Then
        val exception = assertThrows(NoSuchElementException::class.java) {
            updateTaskUseCase.updateTask(updatedTask)
        }

        assertEquals("Task not found with id: $taskId", exception.message)
        verify(taskRepository).findById(taskId)
    }
}