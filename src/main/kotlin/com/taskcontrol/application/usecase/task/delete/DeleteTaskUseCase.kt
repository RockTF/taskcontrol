package com.taskcontrol.application.usecase.task.delete

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeleteTaskUseCase(
    private val taskRepository: ITaskRepository,
    private val getCurrentUser: IGetCurrentUser
) : IDeleteTaskUseCase {

    override fun deleteTask(taskId: UUID) {
        val task = taskRepository.findById(taskId)
            ?: throw NoSuchElementException("Task not found with id: $taskId")

        val user = getCurrentUser.getCurrentUser()

        when {
            user.role == Role.ADMIN -> {
                taskRepository.deleteById(taskId)
            }
            user.id == task.userId -> {
                taskRepository.deleteById(taskId)
            }
            else -> {
                throw IllegalAccessException("User is not allowed to delete this task")
            }
        }
    }
}
