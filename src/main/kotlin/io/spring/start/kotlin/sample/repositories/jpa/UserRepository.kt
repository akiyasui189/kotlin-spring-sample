package io.spring.start.kotlin.sample.repositories.jpa

import io.spring.start.kotlin.sample.entities.tables.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User,Long>