package com.playgroundsocial.config

import com.playgroundsocial.auth.FacebookSignInAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.social.UserIdSource
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer
import org.springframework.social.config.annotation.EnableSocial
import org.springframework.social.config.annotation.SocialConfigurer
import org.springframework.social.config.annotation.SocialConfigurerAdapter
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository
import org.springframework.social.connect.web.ConnectController
import org.springframework.social.connect.web.ProviderSignInController
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.social.connect.web.SignInAdapter
import org.springframework.social.facebook.connect.FacebookConnectionFactory
import org.springframework.social.security.AuthenticationNameUserIdSource
import org.springframework.social.twitter.connect.TwitterConnectionFactory
import javax.sql.DataSource


@Configuration
@EnableSocial
class SocialConfig : SocialConfigurer {

    @Autowired
    internal var dataSource: DataSource? = null

    override fun getUserIdSource() = AuthenticationNameUserIdSource()

    override fun getUsersConnectionRepository(connectionFactoryLocator: ConnectionFactoryLocator): UsersConnectionRepository {
        return JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText())
    }

    override fun addConnectionFactories(cfConfig: ConnectionFactoryConfigurer, env: Environment) {
        cfConfig.addConnectionFactory(TwitterConnectionFactory(
                env.getProperty("spring.social.twitter.app-id"),
                env.getProperty("spring.social.twitter.app-secret")))
        val facebookConnectionFactory = FacebookConnectionFactory(
                env.getProperty("spring.social.facebook.app-id"),
                env.getProperty("spring.social.facebook.app-secret"))
        facebookConnectionFactory.setScope("public_profile,email")
        cfConfig.addConnectionFactory(facebookConnectionFactory)
    }
}


//class SocialConfig : SocialConfigurerAdapter() {
//    @Autowired
//    private lateinit var dataSource: DataSource
//
//    override fun addConnectionFactories(connectionFactoryConfigurer: ConnectionFactoryConfigurer, environment: Environment) {
//        val factory = FacebookConnectionFactory(
//                environment.getProperty("spring.social.facebook.app-id"),
//                environment.getProperty("spring.social.facebook.app-secret")
//        )
//        factory.supportsStateParameter()
//        factory.scope = ("public_profile,email")
//        connectionFactoryConfigurer.addConnectionFactory(factory)
//
//        connectionFactoryConfigurer.addConnectionFactory(TwitterConnectionFactory(
//                environment.getProperty("spring.social.twitter.app-id"),
//                environment.getProperty("spring.social.twitter.app-secret")))
//    }
//
//
//
//    override fun getUserIdSource(): UserIdSource {
//        return AuthenticationNameUserIdSource()
//    }
//
////    override fun getUsersConnectionRepository(connectionFactoryLocator: ConnectionFactoryLocator): UsersConnectionRepository {
////        return JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText())
////    }
//
//    @Bean
//    fun connectController(connectionFactoryLocator: ConnectionFactoryLocator, connectionRepository: ConnectionRepository): ConnectController {
//        return ConnectController(connectionFactoryLocator, connectionRepository)
//    }
//
//    @Bean
//    fun providerSignInUtils(connectionFactoryLocator: ConnectionFactoryLocator, usersConnectionRepository: UsersConnectionRepository): ProviderSignInUtils {
//        return ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository)
//    }
//}
