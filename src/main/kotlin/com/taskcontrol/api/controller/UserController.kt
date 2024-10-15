package com.taskcontrol.api.controller

import com.taskcontrol.application.usecase.user.delete.IDeleteUserUseCase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "User", description = "User Management API")
@RestController
@RequestMapping("/user")
class UserController(
    private val deleteUserUseCase: IDeleteUserUseCase,
) {
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UUID) = deleteUserUseCase.deleteUser(userId)
}
