package com.taskcontrol.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.UUID

data class TaskDto (
    val id: UUID,
    val userId: UUID,
    val title: String,
    val description: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val deadline: LocalDateTime,
    val priority: String,
    val status: String
)