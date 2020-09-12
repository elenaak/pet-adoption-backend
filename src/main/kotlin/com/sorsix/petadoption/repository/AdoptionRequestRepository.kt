package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.AdoptionRequest
import org.springframework.data.jpa.repository.JpaRepository

interface AdoptionRequestRepository : JpaRepository<AdoptionRequest, Long> {
    fun findAdoptionRequestByPetIdOrderByTimeDesc(pet_id: Long): List<AdoptionRequest>
}