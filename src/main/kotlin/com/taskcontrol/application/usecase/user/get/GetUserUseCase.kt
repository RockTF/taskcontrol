package com.taskcontrol.application.usecase.user.get

import com.taskcontrol.application.model.User
import com.taskcontrol.application.port.IUserRepository
import org.springframework.stereotype.Service

@Service
class GetUserUseCase(
    private val userRepository: IUserRepository
) : IGetUserUseCase {
    override fun getAllUsers(): List<User> = userRepository.findAll()
}
