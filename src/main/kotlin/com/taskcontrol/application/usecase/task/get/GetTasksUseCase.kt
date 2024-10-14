package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.application.model.Task
import com.taskcontrol.domain.TaskDto
import com.taskcontrol.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetTasksUseCase(
    private val taskRepository: TaskRepository
): IGetTasksUseCase {

    override fun getTasksByUser(userId: UUID): List<TaskDto> = taskRepository.findAllByUserId(userId).let ( TaskDtoMapper::toDtos )
}