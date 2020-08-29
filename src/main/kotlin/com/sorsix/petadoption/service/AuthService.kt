package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.UserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(val passwordEncoder: BCryptPasswordEncoder,
                  val userDetailsService: UserDetailsService) {

    fun getCurrentUserId(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    fun getCurrentUser(): UserDetails{
        return userDetailsService.loadUserByUsername(getCurrentUserId())
    }
    fun registerUser(username: String, password: String, email: String) {
        userDetailsService.register(username, passwordEncoder.encode(password), email)
    }

}