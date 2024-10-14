package com.taskcontrol.application.usecase.task.update

import com.taskcontrol.application.model.Task
import com.taskcontrol.domain.TaskDto

interface IUpdateTaskUseCase {
    fun updateTask(task: Task): TaskDto
}