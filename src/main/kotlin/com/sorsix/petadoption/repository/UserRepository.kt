package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>