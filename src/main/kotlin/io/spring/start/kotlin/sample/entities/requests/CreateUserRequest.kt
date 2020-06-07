package io.spring.start.kotlin.sample.entities.requests

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateUserRequest(
        @field:NotNull
        @field:Size(min = 3, max = 32)
        val account: String,
        @field:NotNull
        @field:Email
        val email: String,
        @field:NotNull
        @field:Size(min = 8, max = 64)
        val password: String,
        @field:NotNull
        //@field:Size(min = 8, max = 64)
        val confirmPassword: String)