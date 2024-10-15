package com.taskcontrol.application.usecase.user

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.port.IUserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ChangeUserRoleUseCase(
    private val userRepository: IUserRepository
) : IChangeUserRoleUseCase {
    override fun changeUserRole(userId: UUID, role: Role) = userRepository.changeUserRole(userId, role)
}
