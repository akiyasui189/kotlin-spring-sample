package io.spring.start.kotlin.sample

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

// FIXME: 必要であれば有効にする
@Disabled
@SpringBootTest
@ActiveProfiles("test")
class KotlinSpringSampleApplicationTests {

	@Test
	fun contextLoads() {
	}

}
