package com.playgroundsocial.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.Connection
import org.springframework.social.connect.web.SignInAdapter
import org.springframework.social.security.SocialAuthenticationToken
import org.springframework.social.security.SocialUserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.context.request.NativeWebRequest
import org.slf4j.LoggerFactory

@Component
class SimpleSignInAdapter : SignInAdapter {

    private val log = LoggerFactory.getLogger(javaClass.getName())

    @Autowired
    private val socialUserDetailsService: SocialUserDetailsService? = null

    override fun signIn(userId: String, connection: Connection<*>, request: NativeWebRequest): String {

        log.info("User {} signed in", connection.displayName)

        val socialUserDetails = socialUserDetailsService!!.loadUserByUserId(userId)

        val authentication = SocialAuthenticationToken(connection, socialUserDetails, null, socialUserDetails.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        return "/"
    }

}

//class FacebookSignInAdapter : SignInAdapter {
//
//    override fun signIn(userId: String, connection: Connection<*>, request: NativeWebRequest): String? {
//        SecurityContextHolder.getContext().authentication =
//                UsernamePasswordAuthenticationToken(
//                        connection.displayName,
//                        null,
//                        arrayListOf(SimpleGrantedAuthority("FACEBOOK_USER"))
//                )
//
//        return null
//    }
//}
