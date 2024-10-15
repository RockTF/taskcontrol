package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.model.Task
import com.taskcontrol.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetTasksUseCase(
    private val taskRepository: TaskRepository
) : IGetTasksUseCase {
    override fun getTasksByUser(userId: UUID): List<Task> = taskRepository
        .findAllByUserId(userId)
}
