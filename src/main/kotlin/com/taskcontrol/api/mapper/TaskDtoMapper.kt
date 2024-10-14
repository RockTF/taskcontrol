package com.taskcontrol.api.mapper

import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.domain.TaskDto

object TaskDtoMapper {
    fun toDto(task: Task): TaskDto {
        return TaskDto(
            id = task.id,
            userId = task.userId,
            title = task.title,
            description = task.description,
            deadline = task.deadline,
            priority = task.priority.toString(),
            status = task.status.toString()
        )
    }

    fun toDtos(tasks: List<Task>): List<TaskDto> {
        return tasks.map { toDto(it) }
    }

    fun toModel(taskDto: TaskDto): Task {
        return Task(
            id = taskDto.id,
            userId = taskDto.userId,
            title = taskDto.title,
            description = taskDto.description,
            deadline = taskDto.deadline,
            priority = Priority.valueOf(taskDto.priority),
            status = Status.valueOf(taskDto.status)
        )
    }
}