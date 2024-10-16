package com.taskcontrol.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.taskcontrol.config.TestSecurityConfig
import com.taskcontrol.application.usecase.task.update.IUpdateTaskUseCase
import com.taskcontrol.application.usecase.task.create.ICreateTaskUseCase
import com.taskcontrol.domain.TaskDto
import com.taskcontrol.api.mapper.TaskDtoMapper
import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.http.MediaType
import java.time.LocalDate
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(TaskController::class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig::class)
class TaskControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var updateTaskUseCase: IUpdateTaskUseCase

    @MockBean
    private lateinit var createTaskUseCase: ICreateTaskUseCase

    @MockBean
    private lateinit var taskDtoMapper: TaskDtoMapper

    @BeforeEach
    fun setup() {
        updateTaskUseCase = mockk<IUpdateTaskUseCase>(relaxed = true)
        createTaskUseCase = mockk<ICreateTaskUseCase>(relaxed = true)
        taskDtoMapper = spyk(TaskDtoMapper)
    }

//    @Test
//    fun testUpdateTask() {
//        val taskDto = TaskDto(
//            UUID.randomUUID(),
//            UUID.randomUUID(),
//            "Updated Task",
//            "Updated Description",
//            LocalDate.of(2024, 12, 31),
//            "HIGH",
//            "PENDING"
//        )
//
//        val taskSlot = slot<TaskDto>()
//        every { taskDtoMapper.toModel(capture(taskSlot)) } returns Task(
//            id = taskDto.id,
//            userId = taskDto.userId,
//            title = taskDto.title,
//            description = taskDto.description,
//            deadline = taskDto.deadline,
//            priority = Priority.valueOf(taskDto.priority),
//            status = Status.valueOf(taskDto.status)
//        )
//
//        val taskJson = objectMapper.writeValueAsString(taskDto)
//
//        mockMvc.perform(put("/tasks")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(taskJson))
//            .andExpect(status().isOk)
//
//        // Verify that the mock method was called
//        verify { updateTaskUseCase.updateTask(any<Task>()) }
//        verify { taskDtoMapper.toModel(taskDto) }
//    }
}