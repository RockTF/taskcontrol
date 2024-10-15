package com.taskcontrol.domain

import java.util.UUID

data class UserTaskCountDto(
    val userId: UUID,
    val username: String,
    val taskCount: Long
)
