package com.playgroundsocial.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.social.config.annotation.SocialConfiguration
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInController
import org.springframework.social.security.SpringSocialConfigurer

import javax.sql.DataSource


@Configuration
@Import(SocialConfiguration::class)
class SecurityConfig() : WebSecurityConfigurerAdapter() {

    val springSocialConfigurer = SpringSocialConfigurer()

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var connectionFactoryLocator: ConnectionFactoryLocator

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login*", "/signin/**", "/signup./&&").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
//            .apply(springSocialConfigurer
            .apply(SpringSocialConfigurer().postLoginUrl("/login")
//                    .connectionAddedRedirectUrl("/login").defaultFailureUrl("/login?error=twitter")
            )
            .and()
            .logout()
        // @formatter:on
    }
}
