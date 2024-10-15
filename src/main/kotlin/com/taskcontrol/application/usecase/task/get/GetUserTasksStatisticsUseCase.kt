package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.port.ITaskRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetUserTasksStatisticsUseCase(
    private val taskRepository: ITaskRepository
) : IGetUserTasksStatisticsUseCase {
    override fun getUserTasksStatistics(): Map<UUID, Long> = taskRepository.countTasksPerUser()
}