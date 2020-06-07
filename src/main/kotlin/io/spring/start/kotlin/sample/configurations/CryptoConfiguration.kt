package io.spring.start.kotlin.sample.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class CryptoConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun textEncryptor(): TextEncryptor = Encryptors.delux("1234", "5678")

}