package com.taskcontrol.application.usecase.task.update

import com.taskcontrol.application.model.Task
import com.taskcontrol.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class UpdateTaskUseCase(
    private val taskRepository: TaskRepository
) : IUpdateTaskUseCase {
    override fun updateTask(task: Task): Task = taskRepository.save(task)
}
