package com.taskcontrol.application.task.delete

import com.taskcontrol.application.model.Task
import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.User
import com.taskcontrol.application.model.Role
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.task.delete.DeleteTaskUseCase
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class DeleteTaskUseCaseTest {

    @Mock
    private lateinit var taskRepository: ITaskRepository

    @Mock
    private lateinit var getCurrentUser: IGetCurrentUser

    @InjectMocks
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

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
    fun `should delete task for admin`() {
        // Given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val adminUser = User(UUID.randomUUID(), "admin", "password", Role.ADMIN)
        val task = createTask(taskId, userId)

        `when`(taskRepository.findById(taskId)).thenReturn(task)
        `when`(getCurrentUser.getCurrentUser()).thenReturn(adminUser)

        // When
        deleteTaskUseCase.deleteTask(taskId)

        // Then
        verify(taskRepository).deleteById(taskId)
    }

    @Test
    fun `should delete task for owner`() {
        // Given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val ownerUser = User(userId, "user", "password", Role.USER)
        val task = createTask(taskId, userId)

        `when`(taskRepository.findById(taskId)).thenReturn(task)
        `when`(getCurrentUser.getCurrentUser()).thenReturn(ownerUser)

        // When
        deleteTaskUseCase.deleteTask(taskId)

        // Then
        verify(taskRepository).deleteById(taskId)
    }

    @Test
    fun `should throw exception for non-owner non-admin`() {
        // Given
        val taskId = UUID.randomUUID()
        val ownerUserId = UUID.randomUUID()
        val nonOwnerUserId = UUID.randomUUID()
        val nonOwnerUser = User(nonOwnerUserId, "user", "password", Role.USER)
        val task = createTask(taskId, ownerUserId)

        `when`(taskRepository.findById(taskId)).thenReturn(task)
        `when`(getCurrentUser.getCurrentUser()).thenReturn(nonOwnerUser)

        // When / Then
        val exception = assertThrows(IllegalAccessException::class.java) {
            deleteTaskUseCase.deleteTask(taskId)
        }

        assertEquals("User is not allowed to delete this task", exception.message)

        verify(taskRepository, never()).deleteById(taskId)
    }

    @Test
    fun `should throw exception if task not found`() {
        // Given
        val taskId = UUID.randomUUID()

        `when`(taskRepository.findById(taskId)).thenReturn(null)

        // When / Then
        val exception = assertThrows(NoSuchElementException::class.java) {
            deleteTaskUseCase.deleteTask(taskId)
        }

        assertEquals("Task not found with id: $taskId", exception.message)
    }
}