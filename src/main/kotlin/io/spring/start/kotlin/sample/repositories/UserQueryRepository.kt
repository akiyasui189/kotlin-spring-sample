package io.spring.start.kotlin.sample.repositories

import io.spring.start.kotlin.sample.entities.tables.User
import io.spring.start.kotlin.sample.repositories.mappers.UserMapper
import org.springframework.stereotype.Repository

@Repository
class UserQueryRepository (val userMapper: UserMapper) {

    fun findAll (): List<User> {
        return userMapper.findAll()
    }

    fun findByUsernameOrEmail (username: String, email: String) : List<User>{
        return userMapper.findByUsernameOrEmail(username, email)
    }

}