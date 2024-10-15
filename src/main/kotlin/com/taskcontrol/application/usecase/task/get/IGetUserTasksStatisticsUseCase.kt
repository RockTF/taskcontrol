package com.taskcontrol.application.usecase.task.get

import java.util.UUID

interface IGetUserTasksStatisticsUseCase {
    fun getUserTasksStatistics(): Map<UUID, Long>
}