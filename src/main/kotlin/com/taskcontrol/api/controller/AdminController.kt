package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.api.mapper.UserDtoMapper
import com.taskcontrol.application.model.Role
import com.taskcontrol.application.usecase.task.csv.IExportTaskStatisticsUseCase
import com.taskcontrol.application.usecase.task.delete.IDeleteTaskUseCase
import com.taskcontrol.application.usecase.task.get.IGetAllTasksUseCase
import com.taskcontrol.application.usecase.task.get.IGetTaskStatisticsUseCase
import com.taskcontrol.application.usecase.task.update.IUpdateTaskUseCase
import com.taskcontrol.application.usecase.user.IChangeUserRoleUseCase
import com.taskcontrol.application.usecase.user.delete.IDeleteUserUseCase
import com.taskcontrol.application.usecase.user.get.IGetUserStatisticsUseCase
import com.taskcontrol.application.usecase.user.get.IGetUserUseCase
import com.taskcontrol.domain.TaskDto
import com.taskcontrol.domain.UserDto
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
    private val exportTaskStatisticsUseCase: IExportTaskStatisticsUseCase
) {
    @GetMapping("/users")
    fun getAllUsers(): List<UserDto> = getUserUseCase.getAllUsers()
        .map(UserDtoMapper::toDto)

    @DeleteMapping("/users/{userId}")
    fun deleteUser(@PathVariable userId: UUID) = deleteUserUseCase.deleteUser(userId)

    @PostMapping("/users/role/{userId}")
    fun changeUserRole(@PathVariable userId: UUID, @RequestBody role: Role) = changeUserRoleUseCase.changeUserRole(userId, role)

    @GetMapping("/tasks")
    fun getAllTasks(): List<TaskDto> = getAllTasksUseCase.findAllTasks()
        .map(TaskDtoMapper::toDto)

    @PutMapping("/tasks")
    fun updateTask(@RequestBody taskDto: TaskDto) = updateTaskUseCase.updateTask(TaskDtoMapper.toModel(taskDto))

    @DeleteMapping("/tasks/{taskId}")
    fun deleteTask(@PathVariable taskId: UUID) = deleteTaskUseCase.deleteTask(taskId)

    @GetMapping("/statistics/tasks")
    fun getTaskStatistics() = getTaskStatisticsUseCase.getTaskStatistics()

    @GetMapping("/statistics/users")
    fun getUserStatistics() = getUserStatisticsUseCase.getUserStatistics()

    @GetMapping("/export/tasks")
    fun exportTaskStatistics(): String = exportTaskStatisticsUseCase.exportTaskStatisticsToCSV()
}
