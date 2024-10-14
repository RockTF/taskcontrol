package com.taskcontrol.application.usecase.task.delete

import com.taskcontrol.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
): IDeleteTaskUseCase {
    override fun deleteTask(taskId: UUID) = taskRepository.deleteById(taskId)
}