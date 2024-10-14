package com.taskcontrol.api.mapper

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.User
import com.taskcontrol.domain.UserDto

object UserDtoMapper {
    fun toDto(user: User): UserDto {
        return UserDto(
            id = user.id,
            username = user.username,
            password = user.password,
            role = user.role.toString()
        )
    }

    fun toModel(userDto: UserDto): User {
        return User(
            id = userDto.id,
            username = userDto.username,
            password = userDto.password,
            role = Role.valueOf(userDto.role)
        )
    }
}
