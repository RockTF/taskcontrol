package com.taskcontrol.application.usecase.task.create

import com.taskcontrol.application.model.Task

interface ICreateTaskUseCase {
    fun createTask(task: Task): Task
}