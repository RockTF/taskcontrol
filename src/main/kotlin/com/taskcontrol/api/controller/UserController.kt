package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.UserDtoMapper
import com.taskcontrol.application.usecase.user.create.ICreateUserUseCase
import com.taskcontrol.application.usecase.user.delete.IDeleteUserUseCase
import com.taskcontrol.domain.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController(
    private val createUserUseCase: ICreateUserUseCase,
    private val deleteUserUseCase: IDeleteUserUseCase,
    private val passwordEncoder: PasswordEncoder
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserDto): UserDto {
        val userModel = UserDtoMapper.toModel(user).copy(
            password = passwordEncoder.encode(user.password)
        )
        return UserDtoMapper.toDto(createUserUseCase.registerUser(userModel))
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UUID) = deleteUserUseCase.deleteUser(userId)
}
