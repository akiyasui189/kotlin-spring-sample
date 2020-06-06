package io.spring.start.kotlin.sample.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloController () {

    @GetMapping("/world")
    fun helloWorld (): String = "hello world!"

}