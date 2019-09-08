package com.playgroundsocial.config

import com.playgroundsocial.service.UserService
import org.apache.commons.logging.LogFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.web.SignInAdapter
import org.springframework.web.context.request.NativeWebRequest
import java.util.HashSet

class SocialSignInAdapter(private val userService: UserService) : SignInAdapter {

    override fun signIn(localUserId: String, connection: org.springframework.social.connect.Connection<*>, request: NativeWebRequest): String? {
        val user = userService.getUserByEmail(localUserId)

        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(localUserId, user?.password, HashSet<GrantedAuthority>())

        return null
    }

    companion object {

        private val logger = LogFactory.getLog(SocialSignInAdapter::class.java)
    }
}