package com.taskcontrol.application.usecase.task

import com.taskcontrol.application.model.Task
import java.util.UUID

interface IChangeTaskStatusUseCase {
    fun changeTaskStatusToDone(taskId: UUID): Task
}