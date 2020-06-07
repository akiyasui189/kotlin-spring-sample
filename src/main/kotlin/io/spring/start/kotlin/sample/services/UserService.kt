package io.spring.start.kotlin.sample.services

import io.spring.start.kotlin.sample.entities.dtos.CreateUser
import io.spring.start.kotlin.sample.entities.tables.User
import io.spring.start.kotlin.sample.exceptions.AlreadyExistsException
import io.spring.start.kotlin.sample.repositories.UserQueryRepository
import io.spring.start.kotlin.sample.repositories.jpa.UserRepository
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.streams.toList

@Service
class UserService (val textEncryptor: TextEncryptor,val userRepository: UserRepository, val userQueryRepository: UserQueryRepository) {

    fun createUser(user: CreateUser): User {
        // check
        if (userQueryRepository.findByUsernameOrEmail(user.account, user.email).isNotEmpty())
            throw AlreadyExistsException("The User Already Exists!")
        // registration
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        return userRepository.save(
                User(0,
                        user.account,
                        user.password,
                        "SIGN_UP",
                        textEncryptor.encrypt(user.email),
                        null,
                        currentDateTime,
                        user.account,
                        currentDateTime,
                        user.account,
                        0,
                        false
        ))
    }

    fun getUsersList(): List<User> {
        return userQueryRepository.findAll()
                .map {
                    it.email = textEncryptor.decrypt(it.email)
                    it
                }
                .toList()
    }

}