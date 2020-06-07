package io.spring.start.kotlin.sample.services

import io.spring.start.kotlin.sample.entities.tables.User
import io.spring.start.kotlin.sample.repositories.UserQueryRepository
import io.spring.start.kotlin.sample.repositories.jpa.UserRepository
import org.springframework.security.crypto.encrypt.Encryptors
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class UserServiceTest extends Specification {

    @Unroll
    def "getUserList 件数0" () {
        given:
            def textEncryptor = Encryptors.delux("1234", "5678")
            def userRepository = Mock(UserRepository)
            def userQueryRepository = Mock(UserQueryRepository)
            1 * userQueryRepository.findAll() >> []

            def target = new UserService(
                textEncryptor,
                userRepository,
                userQueryRepository
            )

        when:
            def actual = target.getUsersList()
        then:
            actual.size() == 0
    }

    def "getUserList 件数2" () {
        given:
        def textEncryptor = Encryptors.delux("1234", "5678")
        def userRepository = Mock(UserRepository)
        def userQueryRepository = Mock(UserQueryRepository)
        1 * userQueryRepository.findAll() >> [
                new User(
                        1,
                        "username",
                        "password",
                        "SIGN_UP",
                        textEncryptor.encrypt("email"),
                        null,
                        LocalDateTime.now(),
                        "username",
                        LocalDateTime.now(),
                        "username",
                        1,
                        false
                ),
                new User(
                        2,
                        "username2",
                        "password2",
                        "ACTIVE",
                        textEncryptor.encrypt("email2"),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "username2",
                        LocalDateTime.now(),
                        "username2",
                        1,
                        false
                )
        ]

        def target = new UserService(
                textEncryptor,
                userRepository,
                userQueryRepository
        )

        when:
        def actual = target.getUsersList()
        then:
        actual.size() == 2
        actual.get(0).userId == 1
        actual.get(0).account.equals("username")
        actual.get(0).password.equals("password")
        actual.get(0).status.equals("SIGN_UP")
        actual.get(0).email.equals("email")
        actual.get(0).emailVerifiedAt == null
        actual.get(0).createdBy.equals("username")
        actual.get(0).updatedBy.equals("username")
        actual.get(0).version == 1
        !actual.get(0).deleted
        actual.get(1).userId == 2
        actual.get(1).account.equals("username2")
        actual.get(1).password.equals("password2")
        actual.get(1).status.equals("ACTIVE")
        actual.get(1).email.equals("email2")
        actual.get(1).emailVerifiedAt != null
        actual.get(1).createdBy.equals("username2")
        actual.get(1).updatedBy.equals("username2")
        actual.get(1).version == 1
        !actual.get(1).deleted
    }

}
