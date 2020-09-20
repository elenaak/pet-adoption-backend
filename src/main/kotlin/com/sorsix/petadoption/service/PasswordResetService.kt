package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.PasswordResetToken
import com.sorsix.petadoption.domain.exception.InvalidTokenException
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.domain.exception.TokenExpiredException
import com.sorsix.petadoption.repository.PasswordTokenRepository
import com.sorsix.petadoption.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*


@Service
class PasswordResetService(private val userRepository: UserRepository,
                           private val passwordTokenRepository: PasswordTokenRepository,
                           private val emailService: EmailService,
                           val passwordEncoder: BCryptPasswordEncoder) {


    fun resetPasswordViaEmail(email: String): Boolean {
        val user = userRepository.findUserByEmail(email)
        return user.map {
            val token = UUID.randomUUID().toString()
            val dateTime = LocalDateTime.now()
            val passwordResetToken = PasswordResetToken(token, user.get(), dateTime)
            passwordTokenRepository.save(passwordResetToken)
            emailService.sendPasswordResetEmail(passwordResetToken)
            true
        }.orElseThrow {
            InvalidUserIdException()
        }
    }

    fun validateToken(token: String): Boolean {
        val userToken = passwordTokenRepository.findById(token)
        return userToken.map {
            val currentDateTime = LocalDateTime.now()
            val minutes = ChronoUnit.MINUTES.between(userToken.get().timestamp, currentDateTime)
            if (minutes > 60) {
                passwordTokenRepository.delete(userToken.get())
                throw TokenExpiredException()
            }
            true
        }.orElseThrow {
            InvalidTokenException()
        }
    }

    fun resetPassword(password: String, token: String): Boolean {
        val userToken = passwordTokenRepository.findById(token)
        val user = userRepository.findById(userToken.get().user.username).orElseThrow { throw InvalidUserIdException() }
        user.password = passwordEncoder.encode(password)
        userRepository.save(user)
        passwordTokenRepository.delete(userToken.get())
        return true
    }


}