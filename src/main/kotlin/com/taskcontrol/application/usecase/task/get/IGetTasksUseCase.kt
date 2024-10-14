package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import java.util.UUID

interface IGetTasksUseCase {
    fun getTasksByUser(userId: UUID): List<Task>
    fun getTasksByUserAndStatus(userId: UUID, status: Status): List<Task>
    fun findAllTasks(): List<Task>
}
