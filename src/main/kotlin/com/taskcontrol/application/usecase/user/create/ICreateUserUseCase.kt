package com.taskcontrol.application.usecase.user.create

import com.taskcontrol.application.model.User

interface ICreateUserUseCase {
    fun registerUser(user: User): User
}
