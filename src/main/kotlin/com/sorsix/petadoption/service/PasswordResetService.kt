package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.PasswordResetToken
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


    fun resetPasswordViaEmail(email: String): Optional<Boolean> {
        val user = userRepository.findUserByEmail(email);
        if (user.isEmpty) {
            return Optional.ofNullable(false)
        }
        val token = UUID.randomUUID().toString()
        val dateTime = LocalDateTime.now()
        val passwordResetToken = PasswordResetToken(token, user.get(), dateTime)
        passwordTokenRepository.save(passwordResetToken)
        emailService.sendPasswordResetEmail(passwordResetToken)


        return Optional.of(true)
    }

    fun validateToken(token: String): Optional<Boolean> {
        //TODO CHECK TIMESTAMP
        val userToken = passwordTokenRepository.findById(token)
        if (userToken.isEmpty) {
            return Optional.of(false)
        }
        val currentDateTime = LocalDateTime.now()
        val minutes = ChronoUnit.MINUTES.between(userToken.get().timestamp, currentDateTime)
        if (minutes > 60) {
            passwordTokenRepository.delete(userToken.get())
            return Optional.of(false)
        }
        return Optional.of(true)
    }

    fun resetPassword(password: String, token: String): Optional<Boolean> {
        val userToken = passwordTokenRepository.findById(token)
        val user = userRepository.findById(userToken.get().user.username).get()
        user.password = passwordEncoder.encode(password)
        userRepository.save(user)
        passwordTokenRepository.delete(userToken.get())
        return Optional.of(true)
    }


}