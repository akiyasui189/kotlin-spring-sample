package io.spring.start.kotlin.sample.services.applications

import io.spring.start.kotlin.sample.services.transactions.UserTransaction
import io.spring.start.kotlin.sample.entities.dtos.CreateUser
import io.spring.start.kotlin.sample.entities.tables.User
import org.springframework.stereotype.Service

@Service
class UserApplication (var userTransaction: UserTransaction) {

    fun createUser (user: CreateUser) {
        // create user
        userTransaction.createUser(user)
        // send notification
    }

    fun getUsers (): List<User> {
        return userTransaction.getUsers()
    }
}