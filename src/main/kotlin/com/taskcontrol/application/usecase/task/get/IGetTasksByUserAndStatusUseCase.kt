package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import java.util.UUID

interface IGetTasksByUserAndStatusUseCase {
    fun getTasksByUserAndStatus(userId: UUID, status: Status): List<Task>
}
