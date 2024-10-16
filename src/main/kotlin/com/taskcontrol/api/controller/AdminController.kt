package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.api.mapper.UserDtoMapper
import com.taskcontrol.application.model.Role
import com.taskcontrol.application.usecase.task.csv.IExportTaskStatisticsUseCase
import com.taskcontrol.application.usecase.task.delete.IDeleteTaskUseCase
import com.taskcontrol.application.usecase.task.get.IGetAllTasksUseCase
import com.taskcontrol.application.usecase.task.get.IGetTaskStatisticsUseCase
import com.taskcontrol.application.usecase.task.get.IGetUserTasksStatisticsUseCase
import com.taskcontrol.application.usecase.task.update.IUpdateTaskUseCase
import com.taskcontrol.application.usecase.user.IChangeUserRoleUseCase
import com.taskcontrol.application.usecase.user.delete.IDeleteUserUseCase
import com.taskcontrol.application.usecase.user.get.IGetUserStatisticsUseCase
import com.taskcontrol.application.usecase.user.get.IGetUserUseCase
import com.taskcontrol.domain.TaskDto
import com.taskcontrol.domain.UserDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Admin", description = "Admin API")
@RestController
@RequestMapping("/admin")
class AdminController(
    private val deleteUserUseCase: IDeleteUserUseCase,
    private val getUserUseCase: IGetUserUseCase,
    private val changeUserRoleUseCase: IChangeUserRoleUseCase,
    private val getAllTasksUseCase: IGetAllTasksUseCase,
    private val updateTaskUseCase: IUpdateTaskUseCase,
    private val deleteTaskUseCase: IDeleteTaskUseCase,
    private val getTaskStatisticsUseCase: IGetTaskStatisticsUseCase,
    private val getUserStatisticsUseCase: IGetUserStatisticsUseCase,
    private val exportTaskStatisticsUseCase: IExportTaskStatisticsUseCase,
    private val getUserTasksStatisticsUseCase: IGetUserTasksStatisticsUseCase
) {
    @Operation(summary = "Get all users", description = "Returns a list of all users.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @GetMapping("/users")
    fun getAllUsers(): List<UserDto> = getUserUseCase.getAllUsers()
        .map(UserDtoMapper::toDto)

    @Operation(summary = "Delete a user", description = "Deletes a user based on the user ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "User deleted successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @DeleteMapping("/users/{userId}")
    fun deleteUser(@PathVariable userId: UUID) = deleteUserUseCase.deleteUser(userId)

    @Operation(summary = "Change user role", description = "Changes the role of a user based on the user ID and provided role.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User role changed successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @PostMapping("/users/{userId}/role")
    fun changeUserRole(@PathVariable userId: UUID, @RequestBody role: Role) = changeUserRoleUseCase.changeUserRole(userId, role)

    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "List of tasks retrieved successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @GetMapping("/tasks")
    fun getAllTasks(): List<TaskDto> = getAllTasksUseCase.findAllTasks()
        .map(TaskDtoMapper::toDto)

    @Operation(summary = "Update a task", description = "Updates a task based on the provided TaskDto.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Task updated successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @PutMapping("/tasks")
    fun updateTask(@RequestBody taskDto: TaskDto) = updateTaskUseCase.updateTask(TaskDtoMapper.toModel(taskDto))

    @Operation(summary = "Delete a task", description = "Deletes a task based on the task ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Task deleted successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @DeleteMapping("/tasks/{taskId}")
    fun deleteTask(@PathVariable taskId: UUID) = deleteTaskUseCase.deleteTask(taskId)

    @Operation(summary = "Get task statistics", description = "Returns statistics about tasks.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Task statistics retrieved successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @GetMapping("/statistics/tasks")
    fun getTaskStatistics() = getTaskStatisticsUseCase.getTaskStatistics()

    @Operation(summary = "Get user statistics", description = "Returns statistics about users.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User statistics retrieved successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @GetMapping("/statistics/users")
    fun getUserStatistics() = getUserStatisticsUseCase.getUserStatistics()

    @Operation(summary = "Get user tasks statistics", description = "Returns statistics about tasks assigned to users.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User tasks statistics retrieved successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @GetMapping("/statistics/users/tasks")
    fun getUserTasksStatistics() = getUserTasksStatisticsUseCase.getUserTasksStatistics()

    @Operation(summary = "Export task statistics to CSV", description = "Exports task statistics to a CSV file.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Task statistics exported to CSV successfully"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    ])
    @GetMapping("/tasks/export")
    fun exportTaskStatistics(): String = exportTaskStatisticsUseCase.exportTaskStatisticsToCSV()
}