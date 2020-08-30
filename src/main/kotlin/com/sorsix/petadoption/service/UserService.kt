package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun getUserById(username: String): User {
        return userRepository.findById(username).orElseThrow { throw InvalidUserIdException() }
    }
}