package com.taskcontrol.application.task.get

import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.User
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.model.Role
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.task.get.GetTasksByUserAndStatusUseCase
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class GetTasksByUserAndStatusUseCaseTest {

    @Mock
    private lateinit var taskRepository: ITaskRepository

    @Mock
    private lateinit var getCurrentUser: IGetCurrentUser

    @InjectMocks
    private lateinit var getTasksByUserAndStatusUseCase: GetTasksByUserAndStatusUseCase

    private fun createTask(taskId: UUID, userId: UUID, status: Status) = Task(
        id = taskId,
        userId = userId,
        title = "Sample Task",
        description = "Task Description",
        deadline = LocalDate.now().plusDays(1),
        priority = Priority.MEDIUM,
        status = status
    )

    @Test
    fun `should return tasks for admin`() {
        // Given
        val userId = UUID.randomUUID()
        val adminUser = User(UUID.randomUUID(), "admin", "password", Role.ADMIN)
        val status = Status.NOT_STARTED
        val taskId1 = UUID.randomUUID()
        val taskId2 = UUID.randomUUID()
        val task1 = createTask(taskId1, userId, status)
        val task2 = createTask(taskId2, userId, status)
        val expectedTasks = listOf(task1, task2)

        `when`(getCurrentUser.getCurrentUser()).thenReturn(adminUser)
        `when`(taskRepository.findAllByUserIdAndStatus(userId, status)).thenReturn(expectedTasks)

        // When
        val result = getTasksByUserAndStatusUseCase.getTasksByUserAndStatus(userId, status)

        // Then
        assertEquals(expectedTasks, result)
        verify(taskRepository).findAllByUserIdAndStatus(userId, status)
    }

    @Test
    fun `should return tasks for owner`() {
        // Given
        val userId = UUID.randomUUID()
        val ownerUser = User(userId, "user", "password", Role.USER)
        val status = Status.NOT_STARTED
        val taskId1 = UUID.randomUUID()
        val taskId2 = UUID.randomUUID()
        val task1 = createTask(taskId1, userId, status)
        val task2 = createTask(taskId2, userId, status)
        val expectedTasks = listOf(task1, task2)

        `when`(getCurrentUser.getCurrentUser()).thenReturn(ownerUser)
        `when`(taskRepository.findAllByUserIdAndStatus(userId, status)).thenReturn(expectedTasks)

        // When
        val result = getTasksByUserAndStatusUseCase.getTasksByUserAndStatus(userId, status)

        // Then
        assertEquals(expectedTasks, result)
        verify(taskRepository).findAllByUserIdAndStatus(userId, status)
    }

    @Test
    fun `should throw exception for non-owner non-admin`() {
        // Given
        val ownerUserId = UUID.randomUUID()
        val otherUserId = UUID.randomUUID()
        val nonOwnerUser = User(otherUserId, "user", "password", Role.USER)
        val status = Status.NOT_STARTED

        `when`(getCurrentUser.getCurrentUser()).thenReturn(nonOwnerUser)

        // When / Then
        val exception = assertThrows(IllegalAccessException::class.java) {
            getTasksByUserAndStatusUseCase.getTasksByUserAndStatus(ownerUserId, status)
        }

        assertEquals("User is not allowed to view these tasks", exception.message)

        verifyNoInteractions(taskRepository)
    }
}