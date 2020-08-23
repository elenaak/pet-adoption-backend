package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.Pet
import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository : JpaRepository<Pet, Long>