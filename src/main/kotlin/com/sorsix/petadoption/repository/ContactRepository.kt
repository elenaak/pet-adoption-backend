package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.Contact
import org.springframework.data.jpa.repository.JpaRepository

interface ContactRepository : JpaRepository<Contact, Long>