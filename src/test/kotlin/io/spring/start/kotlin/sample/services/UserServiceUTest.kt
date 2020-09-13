package io.spring.start.kotlin.sample.services

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import io.spring.start.kotlin.sample.entities.dtos.CreateUser
import io.spring.start.kotlin.sample.entities.tables.User
import io.spring.start.kotlin.sample.exceptions.AlreadyExistsException
import io.spring.start.kotlin.sample.repositories.UserQueryRepository
import io.spring.start.kotlin.sample.repositories.jpa.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.encrypt.TextEncryptor
import java.time.LocalDateTime

class UserServiceUTest {

    @ExtendWith(MockKExtension::class)
    @Nested
    inner class createUser {

        @MockK
        lateinit var textEncryptor: TextEncryptor
        @MockK
        lateinit var userRepository: UserRepository
        @MockK
        lateinit var userQueryRepository: UserQueryRepository
        @InjectMockKs
        lateinit var target: UserService

        @Test
        fun `createUser failed` () {
            // given
            every {
                textEncryptor.encrypt("email")
            } returns "encrypted"
            every {
                userQueryRepository.findByUsernameOrEmail("username", "encrypted")
            } answers {
                listOf(
                        User(
                                1L,
                                "username",
                                "password",
                                "SIGN_UP",
                                "encrypted",
                                null,
                                LocalDateTime.now(),
                                "username",
                                LocalDateTime.now(),
                                "username",
                                0,
                                false

                        )
                )
            }
            val createUser = CreateUser(
                    "username",
                    "email",
                    "password"
            )
            // when
            val actual = assertThrows<AlreadyExistsException> {
                target.createUser(createUser)
            }
            // then
            Assertions.assertEquals("The User Already Exists!", actual.message)
            verify (exactly = 1) {
                textEncryptor.encrypt("email")
            }
            verify (exactly = 1) {
                userQueryRepository.findByUsernameOrEmail("username", "encrypted")
            }
            verify (exactly = 0) {
                userRepository.save(capture(slot<User>()))
            }
        }

        @Test
        fun `createUser succeed` () {
            // given
            every {
                textEncryptor.encrypt("email")
            } returns "encrypted"
            every {
                userQueryRepository.findByUsernameOrEmail("username", "encrypted")
            } answers {
                listOf()
            }
            every {
                userRepository.save(capture(slot<User>()))
            } answers {
                User(
                        1L,
                        "username",
                        "password",
                        "SIGN_UP",
                        "encrypted",
                        null,
                        LocalDateTime.now(),
                        "username",
                        LocalDateTime.now(),
                        "username",
                        0,
                        false
                )
            }
            val createUser = CreateUser(
                    "username",
                    "email",
                    "password"
            )
            // when
            val actual = target.createUser(createUser)
            // then
            Assertions.assertEquals(1L, actual.userId)
            Assertions.assertEquals("username", actual.account)
            Assertions.assertEquals("password", actual.password)
            Assertions.assertEquals("SIGN_UP", actual.status)
            Assertions.assertEquals("encrypted", actual.email)
            Assertions.assertNull(actual.emailVerifiedAt)
            Assertions.assertNotNull(actual.createdAt)
            Assertions.assertEquals("username", actual.createdBy)
            Assertions.assertNotNull(actual.updatedAt)
            Assertions.assertEquals("username", actual.updatedBy)
            Assertions.assertEquals(0, actual.version)
            Assertions.assertFalse(actual.deleted)
            verify (exactly = 1) {
                textEncryptor.encrypt("email")
            }
            verify (exactly = 1) {
                userQueryRepository.findByUsernameOrEmail("username", "encrypted")
            }
            val captureData = slot<User>()
            verify (exactly = 1) {
                userRepository.save(capture(captureData))
            }
            captureData.captured.let { user ->
                Assertions.assertNull(user.userId)
                Assertions.assertEquals("username", user.account)
                Assertions.assertEquals("password", user.password)
                Assertions.assertEquals("SIGN_UP", user.status)
                Assertions.assertEquals("encrypted", user.email)
                Assertions.assertNull(user.emailVerifiedAt)
                Assertions.assertNotNull(user.createdAt)
                Assertions.assertEquals("username", user.createdBy)
                Assertions.assertNotNull(user.updatedAt)
                Assertions.assertEquals("username", user.updatedBy)
                Assertions.assertEquals(0, user.version)
                Assertions.assertFalse(user.deleted)
            }
        }
    }

    @ExtendWith(MockKExtension::class)
    @Nested
    inner class getUsersList {

        @MockK
        lateinit var textEncryptor: TextEncryptor
        @MockK
        lateinit var userRepository: UserRepository
        @MockK
        lateinit var userQueryRepository: UserQueryRepository
        @InjectMockKs
        lateinit var target: UserService

        @Test
        fun `getUsersList result is empty` () {
            // given
            every {
                userQueryRepository.findAll()
            } answers {
                listOf()
            }
            // when
            val actual = target.getUsersList()
            // then
            Assertions.assertNotNull(actual)
            Assertions.assertEquals(0, actual.size)
            verify (exactly = 1) {
                userQueryRepository.findAll()
            }
            verify (exactly = 0) {
                textEncryptor.decrypt(any())
            }
        }

        @Test
        fun `getUsersList result is not empty` () {
            // given
            every {
                userQueryRepository.findAll()
            } answers {
                listOf(
                        User(
                                1L,
                                "username",
                                "password",
                                "SIGN_UP",
                                "encrypted email",
                                null,
                                LocalDateTime.now(),
                                "username",
                                LocalDateTime.now(),
                                "username",
                                0,
                                false
                        ),
                        User(
                                2L,
                                "username2",
                                "password2",
                                "ACTIVE",
                                "encrypted email2",
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                "username2",
                                LocalDateTime.now(),
                                "username2",
                                1,
                                false
                        )

                )
            }
            every {
                textEncryptor.decrypt("encrypted email")
            } returns "email"
            every {
                textEncryptor.decrypt("encrypted email2")
            } returns "email2"
            // when
            val actual = target.getUsersList()
            // then
            Assertions.assertNotNull(actual)
            Assertions.assertEquals(2, actual.size)
            Assertions.assertEquals(1L, actual.get(0).userId)
            Assertions.assertEquals("username", actual.get(0).account)
            Assertions.assertEquals("password", actual.get(0).password)
            Assertions.assertEquals("SIGN_UP", actual.get(0).status)
            Assertions.assertEquals("email", actual.get(0).email)
            Assertions.assertNull(actual.get(0).emailVerifiedAt)
            Assertions.assertNotNull(actual.get(0).createdAt)
            Assertions.assertEquals("username", actual.get(0).createdBy)
            Assertions.assertNotNull(actual.get(0).updatedAt)
            Assertions.assertEquals("username", actual.get(0).updatedBy)
            Assertions.assertEquals(0, actual.get(0).version)
            Assertions.assertFalse(actual.get(0).deleted)
            Assertions.assertEquals(2L, actual.get(1).userId)
            Assertions.assertEquals("username2", actual.get(1).account)
            Assertions.assertEquals("password2", actual.get(1).password)
            Assertions.assertEquals("ACTIVE", actual.get(1).status)
            Assertions.assertEquals("email2", actual.get(1).email)
            Assertions.assertNotNull(actual.get(1).emailVerifiedAt)
            Assertions.assertNotNull(actual.get(1).createdAt)
            Assertions.assertEquals("username2", actual.get(1).createdBy)
            Assertions.assertNotNull(actual.get(1).updatedAt)
            Assertions.assertEquals("username2", actual.get(1).updatedBy)
            Assertions.assertEquals(1, actual.get(1).version)
            Assertions.assertFalse(actual.get(1).deleted)

            verify (exactly = 1) {
                userQueryRepository.findAll()
            }
            verify (exactly = 1) {
                textEncryptor.decrypt("encrypted email")
            }
            verify (exactly = 1) {
                textEncryptor.decrypt("encrypted email2")
            }
        }

    }
}