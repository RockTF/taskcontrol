package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.model.Task

interface IGetAllTasksUseCase {
    fun findAllTasks(): List<Task>
}
