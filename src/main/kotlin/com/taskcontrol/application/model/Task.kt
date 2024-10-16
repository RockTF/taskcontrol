package com.taskcontrol.application.model

import java.time.LocalDate
import java.util.UUID

data class Task(
    val id: UUID?,
    val userId: UUID,
    val title: String,
    val description: String,
    val deadline: LocalDate,
    val priority: Priority,
    val status: Status
)
