package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.domain.TaskDto
import java.util.UUID

interface IGetTasksUseCase {
    fun getTasksByUser(userId: UUID): List<TaskDto>
}