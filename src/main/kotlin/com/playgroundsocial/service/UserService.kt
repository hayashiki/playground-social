package com.playgroundsocial.service

import com.playgroundsocial.entity.User
import com.playgroundsocial.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.ArrayList

@Service
class UserService (
        @Autowired val userRepository: UserRepository
//        @Autowired val bCryptPasswordEncoder: BCryptPasswordEncoder

) {

    @Transactional
    @Throws(DataIntegrityViolationException::class)
    fun saveUser(user: User) {
//        user.password = bCryptPasswordEncoder.encode(user.password)
        userRepository.save(user)
    }

    @Transactional
    fun updateUser(user: User): Boolean {
        if (userRepository.existsById(user.id)) {
            userRepository.save(user)
            return true
        } else {
            return false
        }
    }

//    fun getUserById(id: String): User? {
//        return JavaToKotlin.OptionalToNullable(userRepository.findById(id))
//    }

    fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun findAll(): List<User> {
        val users = ArrayList<User>()
        userRepository.findAll().forEach { u -> users.add(u) }
        return users
    }
}
