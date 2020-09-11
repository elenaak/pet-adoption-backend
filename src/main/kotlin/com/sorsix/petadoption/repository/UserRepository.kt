package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE (u.email = :email)")
    fun findUserByEmail(@Param("email") email: String): Optional<User>
}