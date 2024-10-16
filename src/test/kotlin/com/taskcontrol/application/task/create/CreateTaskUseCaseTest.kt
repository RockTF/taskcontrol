package com.taskcontrol.application.task.create

import com.taskcontrol.application.model.Task
import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.usecase.task.create.CreateTaskUseCase
import com.taskcontrol.repository.TaskRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
class CreateTaskUseCaseTest {

    @Mock
    private lateinit var taskRepository: TaskRepository

    @InjectMocks
    private lateinit var createTaskUseCase: CreateTaskUseCase

    private fun createSampleTask(taskId: UUID, userId: UUID) = Task(
        id = taskId,
        userId = userId,
        title = "Sample Task",
        description = "Task Description",
        deadline = LocalDate.now().plusDays(1),
        priority = Priority.MEDIUM,
        status = Status.NOT_STARTED
    )

    @Test
    fun `should create task successfully`() {
        // Given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val newTask = createSampleTask(taskId, userId)

        `when`(taskRepository.save(newTask)).thenReturn(newTask)

        // When
        val result = createTaskUseCase.createTask(newTask)

        // Then
        assertNotNull(result)
        assertEquals(newTask, result)
        verify(taskRepository).save(newTask)
    }
}