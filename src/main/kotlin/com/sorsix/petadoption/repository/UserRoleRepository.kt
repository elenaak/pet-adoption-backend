package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository : JpaRepository<UserRole, String>