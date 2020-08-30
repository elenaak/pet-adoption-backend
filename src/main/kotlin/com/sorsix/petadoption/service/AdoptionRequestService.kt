package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.AdoptionRequest
import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.repository.AdoptionRequestRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AdoptionRequestService(val petService: PetService,
                             val authService: AuthService,
                             val userService: UserService,
                             val repository: AdoptionRequestRepository,
                             val emailService: EmailService) {


    fun createAdoptionRequest(petId: Long) {
        val pet = petService.getPetById(petId).orElseThrow { throw InvalidPetIdException() }
        val receiver = pet.contact
        val adopter = userService.getUserById(authService.getCurrentUserId())
        emailService.sendEmail(pet, receiver, adopter)
        repository.save(AdoptionRequest(0, pet.owner, adopter, pet, LocalDateTime.now()))
    }

}