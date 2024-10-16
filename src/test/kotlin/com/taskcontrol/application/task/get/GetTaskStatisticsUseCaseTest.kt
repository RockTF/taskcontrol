package com.taskcontrol.application.task.get

import com.taskcontrol.application.usecase.task.get.GetTaskStatisticsUseCase
import com.taskcontrol.domain.TaskStatisticsDto
import com.taskcontrol.repository.TaskRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GetTaskStatisticsUseCaseTest {

    @Mock
    private lateinit var taskRepository: TaskRepository

    @InjectMocks
    private lateinit var getTaskStatisticsUseCase: GetTaskStatisticsUseCase

    @Test
    fun `should return task statistics`() {
        // Given
        val completedTasks = 5L
        val incompleteTasks = 3L

        `when`(taskRepository.countCompletedTasks()).thenReturn(completedTasks)
        `when`(taskRepository.countIncompleteTasks()).thenReturn(incompleteTasks)

        // When
        val result = getTaskStatisticsUseCase.getTaskStatistics()

        // Then
        assertEquals(TaskStatisticsDto(completedTasks, incompleteTasks), result)
        verify(taskRepository).countCompletedTasks()
        verify(taskRepository).countIncompleteTasks()
    }
}