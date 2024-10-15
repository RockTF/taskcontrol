package com.taskcontrol.application.usecase.task.get

import com.taskcontrol.application.model.Status
import com.taskcontrol.application.model.Task
import com.taskcontrol.application.port.ITaskRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetTasksByUserAndStatusUseCase(
    private val taskRepository: ITaskRepository
) : IGetTasksByUserAndStatusUseCase {
    override fun getTasksByUserAndStatus(userId: UUID, status: Status): List<Task> = taskRepository
        .findAllByUserIdAndStatus(userId, status)
}
