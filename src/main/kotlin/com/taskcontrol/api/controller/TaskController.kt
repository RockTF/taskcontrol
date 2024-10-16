package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.usecase.task.IChangeTaskStatusUseCase
import com.taskcontrol.application.usecase.task.create.ICreateTaskUseCase
import com.taskcontrol.application.usecase.task.delete.IDeleteTaskUseCase
import com.taskcontrol.application.usecase.task.get.IGetTasksByUserAndStatusUseCase
import com.taskcontrol.application.usecase.task.get.IGetTasksUseCase
import com.taskcontrol.application.usecase.task.update.IUpdateTaskUseCase
import com.taskcontrol.domain.TaskDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Tasks", description = "Task Management API")
@RestController
@RequestMapping("/tasks")
class TaskController(
    private val createTaskUseCase: ICreateTaskUseCase,
    private val getTasksUseCase: IGetTasksUseCase,
    private val getTasksByUserUseCase: IGetTasksByUserAndStatusUseCase,
    private val updateTaskUseCase: IUpdateTaskUseCase,
    private val deleteTaskUseCase: IDeleteTaskUseCase,
    private val changeTaskStatusUseCase: IChangeTaskStatusUseCase
) {
    @Operation(summary = "Create a new task", description = "Creates a new task based on the provided TaskDto.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Task successfully created"),
            ApiResponse(responseCode = "400", description = "Invalid input"),
            ApiResponse(responseCode = "401", description = "Unauthorized")
        ]
    )
    @PostMapping
    fun createTask(@RequestBody task: TaskDto): TaskDto = TaskDtoMapper.toModel(task)
        .let { createTaskUseCase.createTask(it) }
        .let { TaskDtoMapper.toDto(it) }

    @Operation(summary = "Get tasks by user ID", description = "Returns a list of tasks assigned to the user.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of tasks returned"),
            ApiResponse(responseCode = "404", description = "User not found"),
            ApiResponse(responseCode = "401", description = "Unauthorized")
        ]
    )
    @GetMapping("/{userId}")
    fun getTasksByUser(@PathVariable userId: UUID): List<TaskDto> = getTasksUseCase.getTasksByUser(userId)
        .let { it.map { task -> TaskDtoMapper.toDto(task) } }

    @Operation(summary = "Get tasks by user ID and status", description = "Returns a list of tasks assigned to the user with the specified status.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of tasks with specified status returned"),
            ApiResponse(responseCode = "404", description = "User not found"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "401", description = "Unauthorized")
        ]
    )
    @GetMapping("/{userId}/status")
    fun getTasksByUserAndStatus(@PathVariable userId: UUID, @RequestParam status: String): List<TaskDto> {
        val statusEnum = Status.valueOf(status.uppercase())
        return getTasksByUserUseCase.getTasksByUserAndStatus(userId, statusEnum)
            .let { it.map { task -> TaskDtoMapper.toDto(task) } }
    }

    @Operation(summary = "Update an existing task", description = "Updates the details of an existing task based on the provided TaskDto.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Task successfully updated"),
            ApiResponse(responseCode = "400", description = "Invalid input"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "401", description = "Unauthorized")
        ]
    )
    @PutMapping
    fun updateTask(@RequestBody task: TaskDto): TaskDto = TaskDtoMapper.toModel(task)
        .let { updateTaskUseCase.updateTask(it) }
        .let { TaskDtoMapper.toDto(it) }

    @Operation(summary = "Delete a task", description = "Deletes the task with the specified ID.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Task successfully deleted"),
            ApiResponse(responseCode = "404", description = "Task not found"),
            ApiResponse(responseCode = "403", description = "Forbidden")
        ]
    )
    @DeleteMapping("/{taskId}")
    fun deleteTask(@PathVariable taskId: UUID) {
        deleteTaskUseCase.deleteTask(taskId)
    }

    @Operation(summary = "Change task status to done", description = "Changes the status of the task with the specified ID to 'DONE'.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Task status successfully changed to 'DONE'"),
            ApiResponse(responseCode = "404", description = "Task not found"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "401", description = "Unauthorized")
        ]
    )
    @PatchMapping("/{taskId}/status")
    fun changeTaskStatusToDone(@PathVariable taskId: UUID): TaskDto {
        return changeTaskStatusUseCase.changeTaskStatusToDone(taskId)
            .let { TaskDtoMapper.toDto(it) }
    }
}
