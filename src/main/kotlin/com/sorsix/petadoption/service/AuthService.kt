package com.sorsix.petadoption.service

import com.sorsix.petadoption.api.dto.SignUpRequest
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.PasswordsNotTheSameException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(val userDetailsService: UserDetailsService,
                  val passwordEncoder: BCryptPasswordEncoder) {

    fun getCurrentUserId(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    fun registerUser(signUpRequest: SignUpRequest): User {
        return userDetailsService.register(signUpRequest.username, passwordEncoder.encode(signUpRequest.password),
                signUpRequest.email, signUpRequest.description)
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        if (!passwordEncoder.matches(oldPassword, userDetailsService.loadUserByUsername(getCurrentUserId()).password))
            throw PasswordsNotTheSameException()
        userDetailsService.changePassword(getCurrentUserId(), passwordEncoder.encode(newPassword))
    }
}