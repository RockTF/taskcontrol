package com.taskcontrol.repository.jpa.mapper

import com.taskcontrol.application.model.User
import com.taskcontrol.application.model.Role
import com.taskcontrol.repository.jpa.entity.UserEntity

object UserMapper {
    fun toModel(userEntity: UserEntity): User {
        return User(
            id = userEntity.id,
            username = userEntity.username,
            password = userEntity.password,
            role = userEntity.role
        )
    }

    fun toModels(userEntities: List<UserEntity>): List<User> {
        return userEntities.map { toModel(it) }
    }

    fun toEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            username = user.username,
            password = user.password,
            role = user.role
        )
    }
}