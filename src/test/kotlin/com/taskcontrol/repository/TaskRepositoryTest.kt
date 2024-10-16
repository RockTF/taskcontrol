package com.taskcontrol.repository

import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.repository.jpa.ITaskJpaRepository
import com.taskcontrol.repository.jpa.IUserJpaRepository
import com.taskcontrol.repository.jpa.entity.TaskEntity
import com.taskcontrol.repository.jpa.entity.UserEntity
import com.taskcontrol.repository.jpa.mapper.TaskMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class TaskRepositoryTest {

    @Mock
    private lateinit var taskJpaRepository: ITaskJpaRepository

    @Mock
    private lateinit var userJpaRepository: IUserJpaRepository

    @InjectMocks
    private lateinit var repository: TaskRepository

    @Test
    fun `should save task`() {
        // Given
        val userId = UUID.randomUUID()
        val taskId = UUID.randomUUID()
        val userEntity = UserEntity(userId, "testuser", "password123", Role.USER)
        val task = Task(taskId, userId, "Test Task", "Description", LocalDate.now(), Priority.HIGH, Status.PENDING)
        val taskEntity = TaskMapper.toEntity(task, userEntity)

        `when`(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity))
        `when`(taskJpaRepository.save(any(TaskEntity::class.java))).thenReturn(taskEntity)

        // When
        val result = repository.save(task)

        // Then
        assertEquals(task, result)
        verify(userJpaRepository).findById(userId)
        verify(taskJpaRepository).save(taskEntity)
    }

    @Test
    fun `should find all tasks`() {
        // Given
        val tasks = listOf<TaskEntity>()
        `when`(taskJpaRepository.findAll()).thenReturn(tasks)

        // When
        val result = repository.findAll()

        // Then
        assertEquals(TaskMapper.toModels(tasks), result)
        verify(taskJpaRepository).findAll()
    }

    @Test
    fun `should find task by id`() {
        // Given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val userEntity = UserEntity(userId, "testuser", "password123", Role.USER)
        val taskEntity = TaskEntity(
            id = taskId,
            title = "Test Task",
            description = "Description",
            deadline = LocalDate.now(),
            priority = Priority.HIGH,
            status = Status.PENDING,
            user = userEntity
        )

        `when`(taskJpaRepository.findById(taskId)).thenReturn(Optional.of(taskEntity))

        // When
        val result = repository.findById(taskId)

        // Then
        assertEquals(TaskMapper.toModel(taskEntity), result)
        verify(taskJpaRepository).findById(taskId)
    }

    @Test
    fun `should delete task by id`() {
        // Given
        val taskId = UUID.randomUUID()
        doNothing().`when`(taskJpaRepository).deleteById(taskId)

        // When
        repository.deleteById(taskId)

        // Then
        verify(taskJpaRepository).deleteById(taskId)
    }

    @Test
    fun `should find all tasks by user id`() {
        // Given
        val userId = UUID.randomUUID()
        val taskEntities = listOf<TaskEntity>()
        `when`(taskJpaRepository.findAllByUserId(userId)).thenReturn(taskEntities)

        // When
        val result = repository.findAllByUserId(userId)

        // Then
        assertEquals(TaskMapper.toModels(taskEntities), result)
        verify(taskJpaRepository).findAllByUserId(userId)
    }

    @Test
    fun `should find all tasks by user id and status`() {
        // Given
        val userId = UUID.randomUUID()
        val status = Status.PENDING
        val taskEntities = listOf<TaskEntity>()
        `when`(taskJpaRepository.findAllByUserIdAndStatus(userId, status)).thenReturn(taskEntities)

        // When
        val result = repository.findAllByUserIdAndStatus(userId, status)

        // Then
        assertEquals(TaskMapper.toModels(taskEntities), result)
        verify(taskJpaRepository).findAllByUserIdAndStatus(userId, status)
    }

    @Test
    fun `should count completed tasks`() {
        // Given
        val count = 10L
        `when`(taskJpaRepository.countCompletedTasks()).thenReturn(count)

        // When
        val result = repository.countCompletedTasks()

        // Then
        assertEquals(count, result)
        verify(taskJpaRepository).countCompletedTasks()
    }

    @Test
    fun `should count incomplete tasks`() {
        // Given
        val count = 5L
        `when`(taskJpaRepository.countIncompleteTasks()).thenReturn(count)

        // When
        val result = repository.countIncompleteTasks()

        // Then
        assertEquals(count, result)
        verify(taskJpaRepository).countIncompleteTasks()
    }

    @Test
    fun `should count tasks per user`() {
        // Given
        val userId = UUID.randomUUID()
        val countMap: List<Array<Any>> = listOf(arrayOf(userId, 15L))
        `when`(taskJpaRepository.countTasksPerUser()).thenReturn(countMap)

        // When
        val result = repository.countTasksPerUser()

        // Then
        val expectedResult = countMap.associate { it[0] as UUID to it[1] as Long }
        assertEquals(expectedResult, result)
        verify(taskJpaRepository).countTasksPerUser()
    }
}