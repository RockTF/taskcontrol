package com.taskcontrol.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.UUID

data class TaskDto(
    val id: UUID?,
    val userId: UUID,
    val title: String,
    val description: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val deadline: LocalDate,
    val priority: String,
    val status: String
)
