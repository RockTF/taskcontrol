package com.taskcontrol.application.usecase.task.update

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import org.springframework.stereotype.Service

@Service
class UpdateTaskUseCase(
    private val taskRepository: ITaskRepository,
    private val getCurrentUser: IGetCurrentUser
) : IUpdateTaskUseCase {

    override fun updateTask(task: Task): Task {
        val existingTask = taskRepository.findById(task.id!!)
            ?: throw NoSuchElementException("Task not found with id: ${task.id}")

        val user = getCurrentUser.getCurrentUser()

        return when {
            user.role == Role.ADMIN -> {
                taskRepository.save(task)
            }
            user.id == existingTask.userId -> {
                taskRepository.save(task)
            }
            else -> {
                throw IllegalAccessException("User is not allowed to update this task")
            }
        }
    }
}
