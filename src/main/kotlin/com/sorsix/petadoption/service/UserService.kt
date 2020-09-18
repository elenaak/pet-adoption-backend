package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Article
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val authService: AuthService) {

    val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun getUserById(username: String): User {
        return userRepository.findById(username).orElseThrow { throw InvalidUserIdException() }
    }

    fun getPetsByUser(): List<Pet> {
        val user = userRepository.findById(authService.getCurrentUserId()).orElseThrow {
            throw InvalidUserIdException()
        }
        return user.pets
    }

    fun getArticlesByAuthor(): List<Article> {
        val user = getUserById(authService.getCurrentUserId())
        return user.articles.sortedByDescending { a -> a.date }
    }

    fun editUser(email: String, desc: String?) {
        val user = userRepository.findById(authService.getCurrentUserId()).orElseThrow {
            throw InvalidUserIdException()
        }
        user.email = email
        user.description = desc
        logger.info("Updating user with username [{}]", user.username)
        userRepository.save(user)
    }

    fun saveUser(u: User) {
        userRepository.save(u)
    }

    fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

}