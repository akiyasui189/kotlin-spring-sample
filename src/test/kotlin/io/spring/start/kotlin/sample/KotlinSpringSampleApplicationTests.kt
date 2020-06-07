package io.spring.start.kotlin.sample

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class KotlinSpringSampleApplicationTests {

	@Test
	fun contextLoads() {
	}

}
