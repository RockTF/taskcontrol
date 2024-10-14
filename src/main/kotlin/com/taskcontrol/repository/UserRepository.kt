package com.taskcontrol.repository

import com.taskcontrol.application.model.User
import com.taskcontrol.application.port.IUserRepository
import com.taskcontrol.repository.jpa.IUserJpaRepository
import com.taskcontrol.repository.jpa.mapper.UserMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserRepository(
    private val userJpaRepository: IUserJpaRepository
) : IUserRepository {
    override fun save(user: User): User = UserMapper.toEntity(user)
        .let { userJpaRepository.save(it) }
        .let { UserMapper.toModel(it) }

    override fun deleteById(userId: UUID) = userJpaRepository.deleteById(userId)

    override fun findAll(): List<User> = userJpaRepository.findAll().let(UserMapper::toModels)
}
