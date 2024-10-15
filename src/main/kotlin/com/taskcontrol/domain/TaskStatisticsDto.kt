package com.taskcontrol.domain

data class TaskStatisticsDto(
    val completedTasks: Long,
    val incompleteTasks: Long
)
