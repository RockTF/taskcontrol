package com.taskcontrol.domain

import java.util.UUID

data class UserDto(
    val id: UUID,
    val username: String,
    var password: String,
    val role: String
)
