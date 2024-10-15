package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.domain.TaskStatisticsDto

interface IGetTaskStatisticsUseCase {
    fun getTaskStatistics(): TaskStatisticsDto
}
