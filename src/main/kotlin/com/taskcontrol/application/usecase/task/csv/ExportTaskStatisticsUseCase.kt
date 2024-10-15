package com.taskcontrol.application.usecase.task.csv

import com.taskcontrol.application.port.ITaskRepository
import org.springframework.stereotype.Service

@Service
class ExportTaskStatisticsUseCase(
    private val taskRepository: ITaskRepository
) : IExportTaskStatisticsUseCase {
    override fun exportTaskStatisticsToCSV(): String {
        val completedTasksCount = taskRepository.countCompletedTasks()
        val incompleteTasksCount = taskRepository.countIncompleteTasks()

        val csvBuilder = StringBuilder()
        csvBuilder.append("Statistic,Count\n")
        csvBuilder.append("Completed tasks,$completedTasksCount\n")
        csvBuilder.append("Incomplete tasks,$incompleteTasksCount\n")
        return csvBuilder.toString()
    }
}
