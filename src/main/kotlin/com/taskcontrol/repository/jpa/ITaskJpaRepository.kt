package com.taskcontrol.repository.jpa

import com.taskcontrol.repository.jpa.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ITaskJpaRepository: JpaRepository<TaskEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<TaskEntity>
}