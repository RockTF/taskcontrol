package com.taskcontrol.application.usecase.task.update

import com.taskcontrol.application.model.Task

interface IUpdateTaskUseCase {
    fun updateTask(task: Task): Task
}
