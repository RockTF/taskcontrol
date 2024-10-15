package com.taskcontrol.repository.jpa

import com.taskcontrol.domain.UserTaskCountDto
import com.taskcontrol.repository.jpa.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface IUserJpaRepository : JpaRepository<UserEntity, UUID> {
    fun findByUsername(username: String): UserEntity?

    @Query(
        "SELECT new com.taskcontrol.domain.UserTaskCountDto(u.id, u.username, COUNT(t)) " +
            "FROM UserEntity u LEFT JOIN TaskEntity t ON u.id = t.user.id " +
            "GROUP BY u.id, u.username"
    )
    fun countTasksByUser(): List<UserTaskCountDto>
}
