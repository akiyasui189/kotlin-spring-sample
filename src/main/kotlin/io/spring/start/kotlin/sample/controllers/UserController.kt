package io.spring.start.kotlin.sample.controllers

import io.spring.start.kotlin.sample.services.applications.UserApplication
import io.spring.start.kotlin.sample.entities.dtos.CreateUser
import io.spring.start.kotlin.sample.entities.requests.CreateUserRequest
import io.spring.start.kotlin.sample.entities.tables.User
import io.spring.start.kotlin.sample.exceptions.AlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.lang.NonNull
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

@RestController
@RequestMapping(
        "/users"//,
        //produces = [MediaType.APPLICATION_JSON_VALUE],
        //consumes = [MediaType.APPLICATION_JSON_VALUE]
)
class UserController (var passwordEncoder: PasswordEncoder, var application: UserApplication) {

    var validator: Validator = Validation.buildDefaultValidatorFactory().validator

    @PostMapping("/create")
    fun createUser(@RequestBody @NonNull request: CreateUserRequest): ResponseEntity<String> {
        // validation
        val constraintViolations: Set<ConstraintViolation<CreateUserRequest>> = validator.validate(request)
        if (constraintViolations.size > 0)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
        // password
        if (!request.password.equals(request.confirmPassword))
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
        // register
        try {
            application.createUser(CreateUser(
                    request.account,
                    request.email,
                    passwordEncoder.encode(request.password)
            ))
        } catch (ex: AlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .build()
    }

    @GetMapping("list")
    fun getUsersList(): ResponseEntity<List<User>> {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(application.getUsers())
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
        }
    }
}