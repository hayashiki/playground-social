package com.playgroundsocial.config

import com.playgroundsocial.auth.FacebookConnectionSignup
import com.playgroundsocial.auth.FacebookSignInAdapter
import com.playgroundsocial.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.social.config.annotation.SocialConfiguration
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInController
import org.springframework.social.security.SpringSocialConfigurer
import java.util.*
import javax.sql.DataSource


@Configuration
@Import(SocialConfiguration::class)
class SecurityConfig() : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var dataSource: DataSource

//    @Value("\${application.URL}")
//    private lateinit var applicationURL: String

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var connectionFactoryLocator: ConnectionFactoryLocator


//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService(userDetailsService)
//    }

    @Bean
    fun providerSignInController(): ProviderSignInController {
        val providerSignInController = ProviderSignInController(
                connectionFactoryLocator,
                usersConnection2Repository(),
                SocialSignInAdapter(userService))
        providerSignInController.setSignUpUrl("/register")
        providerSignInController.setPostSignInUrl("/")
        providerSignInController.setApplicationUrl("/api")
        return providerSignInController
    }

    @Bean
    @Primary
    fun usersConnection2Repository(): UsersConnectionRepository {
        val textEncryptor = Encryptors.noOpText()
        return JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor)
    }


    override fun configure(http: HttpSecurity) {
        val springSocialConfigurer = SpringSocialConfigurer()

        // @formatter:off
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login*", "/signin/**", "/signup./&&").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
//            .apply(springSocialConfigurer)
//            .and()
            .logout()
        // @formatter:on
    }
}
