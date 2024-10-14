package com.taskcontrol.application.usecase.user.delete

import com.taskcontrol.application.port.IUserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeleteUserUseCase(
    private val userRepository: IUserRepository
): IDeleteUserUseCase {
    override fun deleteUser(userId: UUID) = userRepository.deleteById(userId)
}