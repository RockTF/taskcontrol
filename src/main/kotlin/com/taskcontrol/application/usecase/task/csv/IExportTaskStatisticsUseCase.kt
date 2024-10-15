package com.taskcontrol.application.usecase.task.csv

interface IExportTaskStatisticsUseCase {
    fun exportTaskStatisticsToCSV(): String
}
