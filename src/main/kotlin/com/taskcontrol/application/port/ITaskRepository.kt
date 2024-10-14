package com.taskcontrol.application.port

import com.taskcontrol.application.model.Task
import java.util.UUID

interface ITaskRepository {
    fun save(task: Task): Task
    fun findAll(): List<Task>
    fun deleteById(id: UUID)
    fun findAllByUserId(userId: UUID): List<Task>
}