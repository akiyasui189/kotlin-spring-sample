package io.spring.start.kotlin.sample.services

import io.spring.start.kotlin.sample.entities.dtos.CreateUser
import io.spring.start.kotlin.sample.entities.tables.User
import io.spring.start.kotlin.sample.exceptions.AlreadyExistsException
import io.spring.start.kotlin.sample.repositories.UserQueryRepository
import io.spring.start.kotlin.sample.repositories.jpa.UserRepository
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import spock.lang.Specification

import java.time.LocalDateTime

class UserServiceTest extends Specification {

    def "createUser ユーザが存在する" () {
        given:
        def textEncryptor = Mock(TextEncryptor)
        def userRepository = Mock(UserRepository)
        def userQueryRepository = Mock(UserQueryRepository)
        1 * userQueryRepository.findByUsernameOrEmail({
            def arg1 = it as String
            assert arg1 == "username"
        }, {
            def arg2 = it as String
            assert arg2 == "encrypted"
        }) >> [new User(
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
        )]
        1 * textEncryptor.encrypt({
            def arg = it as String
            assert arg == "email"
        }) >> "encrypted"

        def createUser = new CreateUser(
                "username",
                "email",
                "password"
        )

        def target = new UserService(
                textEncryptor,
                userRepository,
                userQueryRepository
        )

        when:
        target.createUser(createUser)

        then:
        def actual = thrown(AlreadyExistsException)
        assert actual.getMessage() == "The User Already Exists!"
    }

    def "createUser ユーザが存在しない" () {
        given:
        def textEncryptor = Mock(TextEncryptor)
        def userRepository = Mock(UserRepository)
        def userQueryRepository = Mock(UserQueryRepository)
        def user = new User(
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
        1 * userQueryRepository.findByUsernameOrEmail({
            def arg1 = it as String
            assert arg1 == "username"
        }, {
            def arg2 = it as String
            assert arg2 == "encrypted"
        }) >> Collections.emptyList()
        1 * textEncryptor.encrypt({
            def arg = it as String
            assert arg == "email"
        }) >> "encrypted"
        1 * userRepository.save({
            def arg = it as User
            assert arg.getUserId() == null
            assert arg.getAccount() == "username"
            assert arg.getPassword() == "password"
            assert arg.getStatus() == "SIGN_UP"
            assert arg.getEmail() == "encrypted"
            assert arg.getEmailVerifiedAt() == null
            assert arg.getCreatedAt() != null
            assert arg.getCreatedBy() == "username"
            assert arg.getUpdatedAt() != null
            assert arg.getUpdatedBy() == "username"
            assert arg.getVersion() == 0
            assert !arg.getDeleted()
        }) >> user

        def createUser = new CreateUser(
                "username",
                "email",
                "password"
        )

        def target = new UserService(
                textEncryptor,
                userRepository,
                userQueryRepository
        )

        when:
        def actual = target.createUser(createUser)

        then:
        assert actual.getUserId() == 1L
        assert actual.getAccount() == "username"
        assert actual.getPassword() == "password"
        assert actual.getStatus() == "SIGN_UP"
        assert actual.getEmail() == "encrypted"
        assert actual.getEmailVerifiedAt() == null
        assert actual.getCreatedAt() != null
        assert actual.getCreatedBy() == "username"
        assert actual.getUpdatedAt() != null
        assert actual.getUpdatedBy() == "username"
        assert actual.getVersion() == 0
        assert !actual.getDeleted()
    }

    // TODO: バグ修正
    def "createUser ユーザが存在しない textEncrptor integration test" () {
        given:
        def textEncryptor = Encryptors.delux("1234", "5678")
        def userRepository = Mock(UserRepository)
        def userQueryRepository = Mock(UserQueryRepository)
        def user = new User(
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
        1 * userQueryRepository.findByUsernameOrEmail({
            def arg1 = it as String
            assert arg1 == "username"
        }, {
            def arg2 = it as String
            //assert arg2 == textEncryptor.encrypt(arg2)
        }) >> Collections.emptyList()
        1 * userRepository.save({
            def arg = it as User
            assert arg.getUserId() == null
            assert arg.getAccount() == "username"
            assert arg.getPassword() == "password"
            assert arg.getStatus() == "SIGN_UP"
            //assert arg.getEmail() == textEncryptor.encrypt("email")
            assert arg.getEmailVerifiedAt() == null
            assert arg.getCreatedAt() != null
            assert arg.getCreatedBy() == "username"
            assert arg.getUpdatedAt() != null
            assert arg.getUpdatedBy() == "username"
            assert arg.getVersion() == 0
            assert !arg.getDeleted()
        }) >> user

        def createUser = new CreateUser(
                "username",
                "email",
                "password"
        )

        def target = new UserService(
                textEncryptor,
                userRepository,
                userQueryRepository
        )

        when:
        def actual = target.createUser(createUser)

        then:
        assert actual.getUserId() == 1L
        assert actual.getAccount() == "username"
        assert actual.getPassword() == "password"
        assert actual.getStatus() == "SIGN_UP"
        assert actual.getEmail() == "encrypted"
        assert actual.getEmailVerifiedAt() == null
        assert actual.getCreatedAt() != null
        assert actual.getCreatedBy() == "username"
        assert actual.getUpdatedAt() != null
        assert actual.getUpdatedBy() == "username"
        assert actual.getVersion() == 0
        assert !actual.getDeleted()
    }

    def "getUserList 件数0" () {
        given:
            def textEncryptor = Mock(TextEncryptor)
            def userRepository = Mock(UserRepository)
            def userQueryRepository = Mock(UserQueryRepository)
            0 * textEncryptor.decrypt(_)
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
        def textEncryptor = Mock(TextEncryptor)
        def userRepository = Mock(UserRepository)
        def userQueryRepository = Mock(UserQueryRepository)
        1 * userQueryRepository.findAll() >> [
                new User(
                        1,
                        "username",
                        "password",
                        "SIGN_UP",
                        "encrypted email",
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
                        "encrypted email2",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "username2",
                        LocalDateTime.now(),
                        "username2",
                        1,
                        false
                )
        ]
        1 * textEncryptor.decrypt({
            def arg = it as String
            assert arg == "encrypted email"
        }) >> "email"
        1 * textEncryptor.decrypt({
            def arg = it as String
            assert arg == "encrypted email2"
        }) >> "email2"

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
        actual.get(0).account == "username"
        actual.get(0).password == "password"
        actual.get(0).status == "SIGN_UP"
        actual.get(0).email == "email"
        actual.get(0).emailVerifiedAt == null
        actual.get(0).createdBy == "username"
        actual.get(0).updatedBy == "username"
        actual.get(0).version == 1
        !actual.get(0).deleted
        actual.get(1).userId == 2
        actual.get(1).account == "username2"
        actual.get(1).password == "password2"
        actual.get(1).status == "ACTIVE"
        actual.get(1).email == "email2"
        actual.get(1).emailVerifiedAt != null
        actual.get(1).createdBy == "username2"
        actual.get(1).updatedBy == "username2"
        actual.get(1).version == 1
        !actual.get(1).deleted
    }

    def "getUserList 件数2 textEncryptor integration test" () {
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
        actual.get(0).account == "username"
        actual.get(0).password == "password"
        actual.get(0).status == "SIGN_UP"
        actual.get(0).email == "email"
        actual.get(0).emailVerifiedAt == null
        actual.get(0).createdBy == "username"
        actual.get(0).updatedBy == "username"
        actual.get(0).version == 1
        !actual.get(0).deleted
        actual.get(1).userId == 2
        actual.get(1).account == "username2"
        actual.get(1).password == "password2"
        actual.get(1).status == "ACTIVE"
        actual.get(1).email == "email2"
        actual.get(1).emailVerifiedAt != null
        actual.get(1).createdBy == "username2"
        actual.get(1).updatedBy == "username2"
        actual.get(1).version == 1
        !actual.get(1).deleted
    }

}
