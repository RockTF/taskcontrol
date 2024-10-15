package com.taskcontrol.application.port

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.User
import java.util.UUID

interface IUserRepository {
    fun save(user: User): User
    fun deleteById(userId: UUID)
    fun findAll(): List<User>
    fun findById(userId: UUID): User?
    fun findByUsername(username: String): User?
    fun changeUserRole(userId: UUID, role: Role)
}
