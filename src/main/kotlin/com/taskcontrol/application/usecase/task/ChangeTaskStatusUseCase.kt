package com.taskcontrol.application.usecase.task

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ChangeTaskStatusUseCase(
    private val taskRepository: ITaskRepository,
    private val getCurrentUser: IGetCurrentUser
) : IChangeTaskStatusUseCase {

    override fun changeTaskStatusToDone(taskId: UUID): Task {
        val task = taskRepository.findById(taskId)
            ?: throw NoSuchElementException("Task not found with id: $taskId")

        val user = getCurrentUser.getCurrentUser()

        return when {
            user.role == Role.ADMIN || user.id == task.userId -> {
                val updatedTask = task.copy(status = Status.COMPLETED)
                taskRepository.save(updatedTask)
                updatedTask
            }
            else -> {
                throw IllegalAccessException("User is not allowed to update the status of this task")
            }
        }
    }
}