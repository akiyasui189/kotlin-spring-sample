package io.spring.start.kotlin.sample.services.transactions

import io.spring.start.kotlin.sample.entities.dtos.CreateUser
import io.spring.start.kotlin.sample.entities.tables.User
import io.spring.start.kotlin.sample.services.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserTransaction (val service: UserService) {

    @Transactional(readOnly = false)
    fun createUser(user: CreateUser) {
        service.createUser(user)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<User> {
        return service.getUsersList()
    }

}