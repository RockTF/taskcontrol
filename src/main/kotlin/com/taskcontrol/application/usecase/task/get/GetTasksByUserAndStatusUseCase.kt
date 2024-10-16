package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.model.Role
import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.usecase.user.get.IGetCurrentUser
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetTasksByUserAndStatusUseCase(
    private val taskRepository: ITaskRepository,
    private val getCurrentUser: IGetCurrentUser
) : IGetTasksByUserAndStatusUseCase {

    override fun getTasksByUserAndStatus(userId: UUID, status: Status): List<Task> {
        val user = getCurrentUser.getCurrentUser()

        return if (user.role == Role.ADMIN || user.id == userId) {
            taskRepository.findAllByUserIdAndStatus(userId, status)
        } else {
            throw IllegalAccessException("User is not allowed to view these tasks")
        }
    }
}
