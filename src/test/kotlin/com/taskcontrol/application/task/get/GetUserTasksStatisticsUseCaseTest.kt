package com.taskcontrol.application.task.get

import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.task.get.GetUserTasksStatisticsUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class GetUserTasksStatisticsUseCaseTest {

    @Mock
    private lateinit var taskRepository: ITaskRepository

    @InjectMocks
    private lateinit var getUserTasksStatisticsUseCase: GetUserTasksStatisticsUseCase

    @Test
    fun `should return user tasks statistics`() {
        // Given
        val userId1 = UUID.randomUUID()
        val userId2 = UUID.randomUUID()
        val expectedStatistics = mapOf(
            userId1 to 5L,
            userId2 to 3L
        )

        `when`(taskRepository.countTasksPerUser()).thenReturn(expectedStatistics)

        // When
        val result = getUserTasksStatisticsUseCase.getUserTasksStatistics()

        // Then
        assertEquals(expectedStatistics, result)
        verify(taskRepository).countTasksPerUser()
    }
}