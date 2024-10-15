package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.model.Task
import com.taskcontrol.application.port.ITaskRepository
import org.springframework.stereotype.Service

@Service
class GetAllTasksUseCase(
    private val taskRepository: ITaskRepository
) : IGetAllTasksUseCase {
    override fun findAllTasks(): List<Task> {
        return taskRepository.findAll()
    }
}
