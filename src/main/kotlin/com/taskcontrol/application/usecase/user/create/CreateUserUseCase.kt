package com.taskcontrol.application.usecase.user.create

import com.taskcontrol.application.model.User
import com.taskcontrol.application.port.IUserRepository
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(
    private val userRepository: IUserRepository
) : ICreateUserUseCase {
    override fun registerUser(user: User): User = userRepository.save(user)
}
