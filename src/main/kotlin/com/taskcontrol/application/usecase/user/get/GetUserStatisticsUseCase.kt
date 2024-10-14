package com.taskcontrol.application.usecase.user.get

import com.taskcontrol.application.port.IUserRepository
import com.taskcontrol.domain.UserTaskCountDto
import org.springframework.stereotype.Service

@Service
class GetUserStatisticsUseCase(
    private val userRepository: IUserRepository
): IGetUserStatisticsUseCase {
    override fun getUserStatistics(): List<UserTaskCountDto> {
        TODO("Not yet implemented")
    }
}