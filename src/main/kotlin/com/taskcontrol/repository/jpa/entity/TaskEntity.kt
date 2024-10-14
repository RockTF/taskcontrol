package com.taskcontrol.repository.jpa.entity

import com.taskcontrol.application.model.Priority
import com.taskcontrol.application.model.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tasks", schema = "public")
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: UUID? = null,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "deadline")
    var deadline: LocalDateTime,

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    var priority: Priority,

    @Column(name = "status", nullable = false)
//    @Type(PostgreSQLEnumType::class)
    @Enumerated(EnumType.STRING)
    var status: Status,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity
)
