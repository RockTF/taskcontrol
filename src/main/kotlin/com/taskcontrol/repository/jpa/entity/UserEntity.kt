package com.taskcontrol.repository.jpa.entity

import com.taskcontrol.application.model.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users", schema = "public")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: UUID? = null,

    @Column(name = "username", nullable = false, unique = true)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role
)
