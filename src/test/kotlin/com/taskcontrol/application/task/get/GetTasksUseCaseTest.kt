package com.taskcontrol.application.task.get

import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.model.User
import com.taskcontrol.application.usecase.task.get.GetTasksUseCase
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import com.taskcontrol.repository.TaskRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class GetTasksUseCaseTest {

    @Mock
    private lateinit var taskRepository: TaskRepository

    @Mock
    private lateinit var getCurrentUser: IGetCurrentUser

    @InjectMocks
    private lateinit var getTasksUseCase: GetTasksUseCase

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
    fun `should return tasks for admin`() {
        // Given
        val userId = UUID.randomUUID()
        val adminUser = User(UUID.randomUUID(), "admin", "password", Role.ADMIN)
        val taskId1 = UUID.randomUUID()
        val taskId2 = UUID.randomUUID()
        val expectedTasks = listOf(createTask(taskId1, userId), createTask(taskId2, userId))

        `when`(getCurrentUser.getCurrentUser()).thenReturn(adminUser)
        `when`(taskRepository.findAllByUserId(userId)).thenReturn(expectedTasks)

        // When
        val result = getTasksUseCase.getTasksByUser(userId)

        // Then
        assertEquals(expectedTasks, result)
        verify(taskRepository).findAllByUserId(userId)
    }

    @Test
    fun `should return tasks for owner`() {
        // Given
        val userId = UUID.randomUUID()
        val ownerUser = User(userId, "user", "password", Role.USER)
        val taskId1 = UUID.randomUUID()
        val taskId2 = UUID.randomUUID()
        val expectedTasks = listOf(createTask(taskId1, userId), createTask(taskId2, userId))

        `when`(getCurrentUser.getCurrentUser()).thenReturn(ownerUser)
        `when`(taskRepository.findAllByUserId(userId)).thenReturn(expectedTasks)

        // When
        val result = getTasksUseCase.getTasksByUser(userId)

        // Then
        assertEquals(expectedTasks, result)
        verify(taskRepository).findAllByUserId(userId)
    }

    @Test
    fun `should throw exception for non-owner non-admin`() {
        // Given
        val ownerUserId = UUID.randomUUID()
        val otherUserId = UUID.randomUUID()
        val nonOwnerUser = User(otherUserId, "user", "password", Role.USER)

        `when`(getCurrentUser.getCurrentUser()).thenReturn(nonOwnerUser)

        // When / Then
        val exception = assertThrows(IllegalAccessException::class.java) {
            getTasksUseCase.getTasksByUser(ownerUserId)
        }

        assertEquals("User is not allowed to view these tasks", exception.message)

        // Ensure no interactions with taskRepository occurred
        verifyNoInteractions(taskRepository)
    }
}