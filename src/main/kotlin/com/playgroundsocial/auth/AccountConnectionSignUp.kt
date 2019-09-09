package com.playgroundsocial.auth

import com.playgroundsocial.entity.User
import com.playgroundsocial.service.UserRegistryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionSignUp
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * ConnectionSignUp
 * provider에서 로그인이 성공적으로 진행하면 Userconnection테이블에서 현재 페이스북으로 가입한유저가있는지 providerId,providerUserId 을통해 검색한다.
 * 만약 Userconnection테이블에서 유저 정보가 없다면 ConnectionSignUp의 execute(Connection<?> connection) 메소드가 호출된다.
 * 이메소드에서 provider에서 제공해주는 유저의 정보을 얻어 올수 있다. 첫번째 로그인이후에는 userconnection테이블에서 유저을 찾게되면 이메소드는 더이상 호출되지 않는다.
 */

const val ROLE_ADMIN = "ADMIN"
const val ROLE_GAMER = "GAMER"

@Component
class AccountConnectionSignUp @Autowired constructor(
        private val userRegistryService: UserRegistryService
        ) : ConnectionSignUp {

    private val log = LoggerFactory.getLogger(javaClass.getName())

    override fun execute(connection: Connection<*>): String {

        log.info("User {} signed in", connection.displayName)

        val profile = connection.fetchUserProfile()

        log.info("User {} email in", profile.email)

        val role = ROLE_ADMIN
        val user = User(profile.email, connection.imageUrl, role)
        userRegistryService.putUser(user)
        return user.email
    }
}

//
////@Service
////class FacebookConnectionSignup(@Autowired val userRepository: UserRepository) : ConnectionSignUp {
//    private val log = LoggerFactory.getLogger(javaClass)
//
//    override fun execute(connection: Connection<*>): String {
//
//        log.info("social connection: {} established", connection.displayName)
////        val profile = connection.fetchUserProfile()
////        log.info("social connection: {} established", profile.email)
//
//        val user = User(
//                username = connection.displayName,
//                password = randomAlphabetic(8),
//                email = "example@example.com",
//                imageUrl = connection.imageUrl
//        )
//        userRepository.save(user)
//        log.info("User {} signed in", connection.displayName)
//
//        return user.username
//    }
//}
