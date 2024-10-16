package com.taskcontrol.application.task.csv

import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.task.csv.ExportTaskStatisticsUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ExportTaskStatisticsUseCaseTest {

    @Mock
    private lateinit var taskRepository: ITaskRepository

    @InjectMocks
    private lateinit var exportTaskStatisticsUseCase: ExportTaskStatisticsUseCase

    @Test
    fun `should export task statistics to CSV`() {
        // Given
        val completedTasksCount = 5L
        val incompleteTasksCount = 3L
        val expectedCSV = "Statistic,Count\n" +
                "Completed tasks,$completedTasksCount\n" +
                "Incomplete tasks,$incompleteTasksCount\n"

        `when`(taskRepository.countCompletedTasks()).thenReturn(completedTasksCount)
        `when`(taskRepository.countIncompleteTasks()).thenReturn(incompleteTasksCount)

        // When
        val result = exportTaskStatisticsUseCase.exportTaskStatisticsToCSV()

        // Then
        assertEquals(expectedCSV, result)
        verify(taskRepository).countCompletedTasks()
        verify(taskRepository).countIncompleteTasks()
    }
}