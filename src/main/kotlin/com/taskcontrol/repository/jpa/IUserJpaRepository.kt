package com.taskcontrol.repository.jpa

import com.taskcontrol.repository.jpa.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IUserJpaRepository: JpaRepository<UserEntity, UUID>