package com.taskcontrol.application.usecase.user

import com.taskcontrol.application.model.Role
import java.util.UUID

interface IChangeUserRoleUseCase {
    fun changeUserRole(userId: UUID, role: Role)
}