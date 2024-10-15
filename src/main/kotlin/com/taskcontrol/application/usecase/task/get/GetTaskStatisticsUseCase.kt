package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.domain.TaskStatisticsDto
import com.taskcontrol.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class GetTaskStatisticsUseCase(
    private val taskRepository: TaskRepository
) : IGetTaskStatisticsUseCase {
    override fun getTaskStatistics(): TaskStatisticsDto {
        val completedTasks = taskRepository.countCompletedTasks()
        val incompleteTasks = taskRepository.countIncompleteTasks()

        return TaskStatisticsDto(
            completedTasks = completedTasks,
            incompleteTasks = incompleteTasks
        )
    }
}
