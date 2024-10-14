package com.taskcontrol.repository.jpa

import com.taskcontrol.application.model.Status
import com.taskcontrol.repository.jpa.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ITaskJpaRepository : JpaRepository<TaskEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<TaskEntity>
    fun findAllByUserIdAndStatus(userId: UUID, status: Status): List<TaskEntity>
}
