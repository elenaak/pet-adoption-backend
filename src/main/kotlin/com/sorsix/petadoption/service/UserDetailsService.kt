package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.domain.exception.RoleNotFoundException
import com.sorsix.petadoption.domain.exception.UsernameAlreadyExists
import com.sorsix.petadoption.repository.UserRepository
import com.sorsix.petadoption.repository.UserRoleRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserDetailsService(private val userRepository: UserRepository,
                         private val roleRepository: UserRoleRepository,
                         private val emailService: EmailService) : UserDetailsService {

    val logger: Logger = LoggerFactory.getLogger(com.sorsix.petadoption.service.UserDetailsService::class.java)

    override fun loadUserByUsername(username: String): com.sorsix.petadoption.domain.UserDetails {
        val user = userRepository.findById(username)
        return user.map { u -> com.sorsix.petadoption.domain.UserDetails(u) }.orElseThrow {
            UsernameNotFoundException(username)
        }
    }

    fun register(username: String, password: String, email: String, description: String?): User {
        if (userRepository.existsById(username))
            throw UsernameAlreadyExists(username)
        val role = roleRepository.findById("ROLE_USER").orElseThrow { throw RoleNotFoundException() }
        val user = userRepository.save(User(username, password, role, email, description))
        emailService.sendWelcomeEmail(user)
        logger.info("Saving user [{}]", user)
        return user
    }

    fun registerAdmin(username: String, password: String, email: String, description: String?): User {
        if (userRepository.existsById(username))
            throw UsernameAlreadyExists(username)
        val role = roleRepository.findById("ROLE_ADMIN").orElseThrow { throw RoleNotFoundException() }
        val admin = User(username, password, role, email, description)
        logger.info("Saving admin [{}]", admin)
        return userRepository.save(admin)
    }

    fun changePassword(username: String, newPassword: String): User {
        val user = userRepository.findById(username).orElseThrow { InvalidUserIdException() }
        user.password = newPassword
        userRepository.save(user)
        return user
    }

}