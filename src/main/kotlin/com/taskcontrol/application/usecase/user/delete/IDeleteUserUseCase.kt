package com.taskcontrol.application.usecase.user.delete

import java.util.*

interface IDeleteUserUseCase {
    fun deleteUser(userId: UUID)
}
