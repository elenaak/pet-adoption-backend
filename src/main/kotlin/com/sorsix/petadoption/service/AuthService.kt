package com.sorsix.petadoption.service

import com.sorsix.petadoption.api.dto.SignUpRequest
import com.sorsix.petadoption.domain.User
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

}