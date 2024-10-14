package com.taskcontrol.repository.jpa.mapper

import com.taskcontrol.application.model.Task
import com.taskcontrol.repository.jpa.entity.TaskEntity
import com.taskcontrol.repository.jpa.entity.UserEntity

object TaskMapper {
    fun toModel(taskEntity: TaskEntity): Task {
        return Task(
            id = taskEntity.id,
            userId = taskEntity.user.id!!,
            title = taskEntity.title,
            description = taskEntity.description ?: "",
            deadline = taskEntity.deadline,
            priority = taskEntity.priority,
            status = taskEntity.status
        )
    }

    fun toModels(taskEntities: List<TaskEntity>): List<Task> {
        return taskEntities.map { toModel(it) }
    }

    fun toEntity(task: Task, userEntity: UserEntity): TaskEntity {
        return TaskEntity(
            id = task.id,
            title = task.title,
            description = task.description,
            deadline = task.deadline,
            priority = task.priority,
            status = task.status,
            user = userEntity
        )
    }
}
