package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.PasswordResetToken
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordTokenRepository : JpaRepository<PasswordResetToken, String> {
}