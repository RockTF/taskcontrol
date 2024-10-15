package com.taskcontrol.application.usecase.user.get

import com.taskcontrol.domain.UserTaskCountDto

interface IGetUserStatisticsUseCase {
    fun getUserStatistics(): List<UserTaskCountDto>
}
