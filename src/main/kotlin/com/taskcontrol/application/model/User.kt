package com.taskcontrol.application.model

import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    var password: String,
    val role: Role
)
