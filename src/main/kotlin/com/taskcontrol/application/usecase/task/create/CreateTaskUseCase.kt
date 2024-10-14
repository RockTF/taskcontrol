package com.taskcontrol.application.usecase.task.create

import com.taskcontrol.application.model.Task
import com.taskcontrol.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class CreateTaskUseCase(
    private val taskRepository: TaskRepository
) : ICreateTaskUseCase {
    override fun createTask(task: Task): Task = taskRepository.save(task)
}
