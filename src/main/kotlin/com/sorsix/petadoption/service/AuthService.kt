package com.sorsix.petadoption.service

import com.sorsix.petadoption.api.dto.SignUpRequest
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.UserRole
import com.sorsix.petadoption.domain.exception.PasswordsNotTheSameException
import com.sorsix.petadoption.repository.UserRoleRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class AuthService(val userDetailsService: UserDetailsService,
                  val passwordEncoder: BCryptPasswordEncoder,
                  val roleRepository: UserRoleRepository) {

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

    @PostConstruct
    fun init() {
        if (!roleRepository.existsById("ROLE_USER"))
            roleRepository.save(UserRole("ROLE_USER"))
        if (!roleRepository.existsById("ROLE_ADMIN")) {
            roleRepository.save(UserRole("ROLE_ADMIN"))
            userDetailsService.registerAdmin("admin", passwordEncoder.encode("adminpsw"),
                    "admin@live.com", null)
        }
    }
}