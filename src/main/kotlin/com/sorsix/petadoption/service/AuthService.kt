package com.sorsix.petadoption.service

import com.sorsix.petadoption.api.SignUpRequest
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.UserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class AuthService(val userDetailsService: UserDetailsService,
                  val passwordEncoder: BCryptPasswordEncoder) {

    fun getCurrentUserId(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    fun getCurrentUser(): UserDetails {
        return userDetailsService.loadUserByUsername(getCurrentUserId())
    }

    fun registerUser(signUpRequest: SignUpRequest): User {
        return userDetailsService.register(signUpRequest.username, passwordEncoder.encode(signUpRequest.password),
                signUpRequest.email)
    }

}