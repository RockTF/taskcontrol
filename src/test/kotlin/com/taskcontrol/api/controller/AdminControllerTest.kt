package com.taskcontrol.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.api.mapper.UserDtoMapper
import com.taskcontrol.config.TestSecurityConfig
import com.taskcontrol.application.usecase.task.csv.IExportTaskStatisticsUseCase
import com.taskcontrol.application.usecase.task.delete.IDeleteTaskUseCase
import com.taskcontrol.application.usecase.task.get.IGetAllTasksUseCase
import com.taskcontrol.application.usecase.task.get.IGetTaskStatisticsUseCase
import com.taskcontrol.application.usecase.task.get.IGetUserTasksStatisticsUseCase
import com.taskcontrol.application.usecase.task.update.IUpdateTaskUseCase
import com.taskcontrol.application.usecase.user.IChangeUserRoleUseCase
import com.taskcontrol.application.usecase.user.delete.IDeleteUserUseCase
import com.taskcontrol.application.usecase.user.get.IGetUserUseCase
import com.taskcontrol.domain.TaskDto
import com.taskcontrol.domain.TaskStatisticsDto
import com.taskcontrol.domain.UserDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.http.MediaType
import java.time.LocalDate
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(AdminController::class)
@Import(TestSecurityConfig::class)
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var deleteUserUseCase: IDeleteUserUseCase

    @MockBean
    lateinit var getUserUseCase: IGetUserUseCase

    @MockBean
    lateinit var changeUserRoleUseCase: IChangeUserRoleUseCase

    @MockBean
    lateinit var getAllTasksUseCase: IGetAllTasksUseCase

    @MockBean
    lateinit var updateTaskUseCase: IUpdateTaskUseCase

    @MockBean
    lateinit var deleteTaskUseCase: IDeleteTaskUseCase

    @MockBean
    lateinit var getTaskStatisticsUseCase: IGetTaskStatisticsUseCase

    @MockBean
    lateinit var exportTaskStatisticsUseCase: IExportTaskStatisticsUseCase

    @MockBean
    lateinit var getUserTasksStatisticsUseCase: IGetUserTasksStatisticsUseCase

    @Test
    fun testGetAllUsers() {
        val users = listOf(UserDto(UUID.randomUUID(), "John Doe", "john@example.com"))
        `when`(getUserUseCase.getAllUsers()).thenReturn(users.map(UserDtoMapper::toModel))

        mockMvc.perform(get("/admin/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].username").value("John Doe"))
    }

    @Test
    fun testDeleteUser() {
        val userId = UUID.randomUUID()
        doNothing().`when`(deleteUserUseCase).deleteUser(userId)

        mockMvc.perform(delete("/admin/users/$userId"))
            .andExpect(status().isOk)
    }

    @Test
    fun testGetAllTasks() {
        val tasks = listOf(TaskDto(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Task 1",
            "Description 1",
            LocalDate.of(2024, 12, 31),
            "HIGH",
            "PENDING"
        ))
        `when`(getAllTasksUseCase.findAllTasks()).thenReturn(tasks.map(TaskDtoMapper::toModel))

        mockMvc.perform(get("/admin/tasks"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title").value("Task 1"))
    }

    @Test
    fun testDeleteTask() {
        val taskId = UUID.randomUUID()
        doNothing().`when`(deleteTaskUseCase).deleteTask(taskId)

        mockMvc.perform(delete("/admin/tasks/$taskId"))
            .andExpect(status().isOk)
    }

    @Test
    fun testGetTaskStatistics() {
        val statistics = TaskStatisticsDto(completedTasks = 5, incompleteTasks = 3)
        `when`(getTaskStatisticsUseCase.getTaskStatistics()).thenReturn(statistics)

        val objectMapper = jacksonObjectMapper()
        val statisticsJson = objectMapper.writeValueAsString(statistics)

        mockMvc.perform(get("/admin/statistics/tasks"))
            .andExpect(status().isOk)
            .andExpect(content().json(statisticsJson))
    }

    @Test
    fun testGetUserTasksStatistics() {
        val statistics = mapOf(
            UUID.randomUUID() to 10L,
            UUID.randomUUID() to 5L
        )
        `when`(getUserTasksStatisticsUseCase.getUserTasksStatistics()).thenReturn(statistics)

        val objectMapper = jacksonObjectMapper()
        val statisticsJson = objectMapper.writeValueAsString(statistics)

        mockMvc.perform(get("/admin/statistics/users/tasks"))
            .andExpect(status().isOk)
            .andExpect(content().json(statisticsJson))
    }

    @Test
    fun testExportTaskStatistics() {
        val export = "Exported CSV data"
        `when`(exportTaskStatisticsUseCase.exportTaskStatisticsToCSV()).thenReturn(export)

        mockMvc.perform(get("/admin/tasks/export"))
            .andExpect(status().isOk)
            .andExpect(content().string(export))
    }
}