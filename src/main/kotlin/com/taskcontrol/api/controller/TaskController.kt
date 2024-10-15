package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.usecase.task.IChangeTaskStatusUseCase
import com.taskcontrol.application.usecase.task.create.ICreateTaskUseCase
import com.taskcontrol.application.usecase.task.delete.IDeleteTaskUseCase
import com.taskcontrol.application.usecase.task.get.IGetTasksUseCase
import com.taskcontrol.application.usecase.task.update.IUpdateTaskUseCase
import com.taskcontrol.domain.TaskDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Tag(name = "Tasks", description = "Task Management API")
@RestController
@RequestMapping("/tasks")
class TaskController(
    private val createTaskUseCase: ICreateTaskUseCase,
    private val getTasksUseCase: IGetTasksUseCase,
    private val updateTaskUseCase: IUpdateTaskUseCase,
    private val deleteTaskUseCase: IDeleteTaskUseCase,
    private val changeTaskStatusUseCase: IChangeTaskStatusUseCase
) {
    @PostMapping
    fun createTask(@RequestBody task: TaskDto): TaskDto = TaskDtoMapper.toModel(task)
        .let { createTaskUseCase.createTask(it) }
        .let { TaskDtoMapper.toDto(it) }

    @GetMapping("/{userId}")
    fun getTasksByUser(@PathVariable userId: UUID): List<TaskDto> = getTasksUseCase.getTasksByUser(userId)
        .let { it.map { task -> TaskDtoMapper.toDto(task) } }

    @GetMapping("/{userId}/status")
    fun getTasksByUserAndStatus(@PathVariable userId: UUID, @RequestParam status: String): List<TaskDto> {
        val statusEnum = Status.valueOf(status.uppercase())
        return getTasksUseCase.getTasksByUserAndStatus(userId, statusEnum)
            .let { it.map { task -> TaskDtoMapper.toDto(task) } }
    }

    @PutMapping
    fun updateTask(@RequestBody task: TaskDto): TaskDto = TaskDtoMapper.toModel(task)
        .let { updateTaskUseCase.updateTask(it) }
        .let { TaskDtoMapper.toDto(it) }

    @DeleteMapping("/{taskId}")
    fun deleteTask(@PathVariable taskId: UUID) {
        deleteTaskUseCase.deleteTask(taskId)
    }

    @PatchMapping("/{taskId}/status")
    fun changeTaskStatusToDone(@PathVariable taskId: UUID): TaskDto {
        return changeTaskStatusUseCase.changeTaskStatusToDone(taskId)
            .let { TaskDtoMapper.toDto(it) }
    }
}
