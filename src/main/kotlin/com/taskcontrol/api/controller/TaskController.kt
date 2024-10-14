package com.taskcontrol.api.controller

import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.application.usecase.task.create.ICreateTaskUseCase
import com.taskcontrol.application.usecase.task.get.IGetTasksUseCase
import com.taskcontrol.domain.TaskDto
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val createTaskUseCase: ICreateTaskUseCase,
    private val getTasksUseCase: IGetTasksUseCase
) {
    @PostMapping
    fun createTask(@RequestBody task: TaskDto): TaskDto = TaskDtoMapper.toModel(task)
        .let { createTaskUseCase.createTask(it) }
        .let { TaskDtoMapper.toDto(it) }

    @GetMapping("/{userId}")
    fun getTasksByUser(@PathVariable userId: UUID): List<TaskDto> = getTasksUseCase.getTasksByUser(userId)
}
