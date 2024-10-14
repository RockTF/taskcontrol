package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.UserDtoMapper
import com.taskcontrol.application.usecase.user.create.ICreateUserUseCase
import com.taskcontrol.application.usecase.user.delete.IDeleteUserUseCase
import com.taskcontrol.domain.UserDto
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController(
    private val createUserUseCase: ICreateUserUseCase,
    private val deleteUserUseCase: IDeleteUserUseCase

) {
    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserDto): UserDto = UserDtoMapper.toModel(user)
        .let { createUserUseCase.registerUser(it) }
        .let { UserDtoMapper.toDto(it) }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UUID) = deleteUserUseCase.deleteUser(userId)

}