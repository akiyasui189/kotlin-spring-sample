package io.spring.start.kotlin.sample.entities.dtos

data class CreateUser (
        val account: String,
        val email: String,
        val password: String)