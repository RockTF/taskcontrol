package com.taskcontrol.application.usecase.task.update

import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.application.model.Task
import com.taskcontrol.domain.TaskDto
import com.taskcontrol.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class UpdateTaskUseCase(
    private val taskRepository: TaskRepository
): IUpdateTaskUseCase {
    override fun updateTask(task: Task): TaskDto = taskRepository.save(task).let ( TaskDtoMapper::toDto )
}