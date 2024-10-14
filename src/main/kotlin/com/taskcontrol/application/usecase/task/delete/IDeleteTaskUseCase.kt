package com.taskcontrol.application.usecase.task.delete

import java.util.*

interface IDeleteTaskUseCase {
    fun deleteTask(taskId: UUID)
}