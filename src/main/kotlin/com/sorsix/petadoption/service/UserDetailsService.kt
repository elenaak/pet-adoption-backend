package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.RoleNotFoundException
import com.sorsix.petadoption.repository.UserRepository
import com.sorsix.petadoption.repository.UserRoleRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val userRepository: UserRepository,
                         private val roleRepository: UserRoleRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): com.sorsix.petadoption.domain.UserDetails {
        val user = userRepository.findById(username)
        return user.map { u -> com.sorsix.petadoption.domain.UserDetails(u) }.orElseThrow {
            UsernameNotFoundException(username)
        }
    }

    fun register(username: String, password: String, email: String) {
        val role = roleRepository.findById("ROLE_USER").orElseThrow { throw RoleNotFoundException() }
        userRepository.save(User(username, password, role, email))
    }


}