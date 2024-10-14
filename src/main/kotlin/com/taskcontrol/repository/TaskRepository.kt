package com.taskcontrol.repository

import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.repository.jpa.ITaskJpaRepository
import com.taskcontrol.repository.jpa.IUserJpaRepository
import com.taskcontrol.repository.jpa.mapper.TaskMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class TaskRepository(
    private val taskJpaRepository: ITaskJpaRepository,
    private val userJpaRepository: IUserJpaRepository
) : ITaskRepository {
    override fun save(task: Task): Task = run {
        val user = userJpaRepository.findById(task.userId)
            .orElseThrow { NoSuchElementException("User not found with id: ${task.userId}") }

        TaskMapper.toEntity(task, user)
            .let { taskJpaRepository.save(it) }
            .let { TaskMapper.toModel(it) }
    }

    override fun findAll(): List<Task> = taskJpaRepository.findAll().let(TaskMapper::toModels)

    override fun deleteById(id: UUID) = taskJpaRepository.deleteById(id)

    override fun findAllByUserId(userId: UUID): List<Task> = taskJpaRepository.findAllByUserId(userId)
        .let(TaskMapper::toModels)

    override fun findAllByUserIdAndStatus(userId: UUID, status: Status): List<Task> = taskJpaRepository
        .findAllByUserIdAndStatus(userId, status)
        .let(TaskMapper::toModels)

    override fun countCompletedTasks(): Long = taskJpaRepository.countCompletedTasks()

    override fun countIncompleteTasks(): Long = taskJpaRepository.countIncompleteTasks()
}
