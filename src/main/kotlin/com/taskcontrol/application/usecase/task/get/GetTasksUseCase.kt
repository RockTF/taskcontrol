package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import com.taskcontrol.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetTasksUseCase(
    private val taskRepository: TaskRepository,
    private val getCurrentUser: IGetCurrentUser
) : IGetTasksUseCase {
    override fun getTasksByUser(userId: UUID): List<Task> {
        val user = getCurrentUser.getCurrentUser()

        return if (user.role == Role.ADMIN || user.id == userId) {
            taskRepository.findAllByUserId(userId)
        } else {
            throw IllegalAccessException("User is not allowed to view these tasks")
        }
    }
}
