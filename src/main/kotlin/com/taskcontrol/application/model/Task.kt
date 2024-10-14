package com.taskcontrol.application.model

import java.time.LocalDateTime
import java.util.UUID

data class Task(
    val id: UUID?,
    val userId: UUID,
    val title: String,
    val description: String,
    val deadline: LocalDateTime,
    val priority: Priority,
    val status: Status
)
