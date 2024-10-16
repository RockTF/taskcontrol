package com.taskcontrol.application.task.get

import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.task.get.GetAllTasksUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.UUID

@ExtendWith(MockitoExtension::class)
internal class GetAllTasksUseCaseTest {

    @Mock
    private lateinit var taskRepository: ITaskRepository

    @InjectMocks
    private lateinit var getAllTasksUseCase: GetAllTasksUseCase

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
    fun `should return all tasks`() {
        // Given
        val taskId1 = UUID.randomUUID()
        val taskId2 = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val task1 = createTask(taskId1, userId)
        val task2 = createTask(taskId2, userId)
        val expectedTasks = listOf(task1, task2)

        `when`(taskRepository.findAll()).thenReturn(expectedTasks)

        // When
        val result = getAllTasksUseCase.findAllTasks()

        // Then
        assertEquals(expectedTasks, result)
        verify(taskRepository).findAll()
    }
}