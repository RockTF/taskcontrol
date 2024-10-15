package com.taskcontrol.application.port

import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import java.util.UUID

interface ITaskRepository {
    fun save(task: Task): Task
    fun findAll(): List<Task>
    fun findById(id: UUID): Task?
    fun deleteById(id: UUID)
    fun findAllByUserId(userId: UUID): List<Task>
    fun findAllByUserIdAndStatus(userId: UUID, status: Status): List<Task>
    fun countCompletedTasks(): Long
    fun countIncompleteTasks(): Long
    fun countTasksPerUser(): Map<UUID, Long>
}
