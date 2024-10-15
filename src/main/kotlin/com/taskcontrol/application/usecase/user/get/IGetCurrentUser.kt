package com.taskcontrol.application.usecase.user.get

import com.taskcontrol.application.model.User

interface IGetCurrentUser {
    fun getCurrentUser(): User
}
