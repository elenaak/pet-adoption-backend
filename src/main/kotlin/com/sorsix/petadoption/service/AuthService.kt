package com.sorsix.petadoption.service

import com.sorsix.petadoption.api.SignUpRequest
import com.sorsix.petadoption.domain.UserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService(val userDetailsService: UserDetailsService) {

    fun getCurrentUserId(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    fun getCurrentUser(): UserDetails {
        return userDetailsService.loadUserByUsername(getCurrentUserId())
    }

    fun registerUser(signUpRequest: SignUpRequest) {
        userDetailsService.register(signUpRequest.username, signUpRequest.password, signUpRequest.email)
    }
}