package io.spring.start.kotlin.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringSampleApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringSampleApplication>(*args)
}
