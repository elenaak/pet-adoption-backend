package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val authService: AuthService) {

    fun getUserById(username: String): User {
        return userRepository.findById(username).orElseThrow { throw InvalidUserIdException() }
    }

    fun getPetsByUser(): List<Pet> {
        val user = userRepository.findById(authService.getCurrentUserId()).orElseThrow {
            throw InvalidUserIdException()
        }
        return user.pets
    }

    fun editUser(email: String, desc: String?) {
        val user = userRepository.findById(authService.getCurrentUserId()).orElseThrow {
            throw InvalidUserIdException()
        }
        user.email = email
        user.description = desc
        userRepository.save(user)
    }

    fun saveUser(u: User) {
        userRepository.save(u)
    }

    fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

}