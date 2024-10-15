package com.taskcontrol.repository.jpa

import com.taskcontrol.application.model.Status
import com.taskcontrol.repository.jpa.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ITaskJpaRepository : JpaRepository<TaskEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<TaskEntity>
    fun findAllByUserIdAndStatus(userId: UUID, status: Status): List<TaskEntity>

    @Query("SELECT COUNT(t) FROM TaskEntity t WHERE t.status = 'COMPLETED'")
    fun countCompletedTasks(): Long

    @Query("SELECT COUNT(t) FROM TaskEntity t WHERE t.status != 'COMPLETED'")
    fun countIncompleteTasks(): Long

    @Query("SELECT t.user.id, COUNT(t) FROM TaskEntity t GROUP BY t.user.id")
    fun countTasksPerUser(): List<Array<Any>>
}
