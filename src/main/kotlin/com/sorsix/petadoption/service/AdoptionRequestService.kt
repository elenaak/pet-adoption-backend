package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.AdoptionRequest
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.repository.AdoptionRequestRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.stream.Collectors

@Service
class AdoptionRequestService(val petService: PetService,
                             val userService: UserService,
                             val repository: AdoptionRequestRepository,
                             val emailService: EmailService) {

    val logger: Logger = LoggerFactory.getLogger(AdoptionRequestService::class.java)

    fun createAdoptionRequest(petId: Long): AdoptionRequest {
        val pet = petService.getPetById(petId)
        val receiver = pet.contact
        val adopter = userService.getCurrentUser()
        emailService.sendEmailToOwner(pet, receiver, adopter)
        val request = AdoptionRequest(0, adopter, pet, LocalDateTime.now())
        logger.info("Saving adoption request [{}]", request)
        return repository.save(request)
    }

    fun getRequestsByPetId(petId: Long): List<User> {
        return repository.findAdoptionRequestByPetIdOrderByTimeDesc(petId)
                .stream().map { it.adopter }.collect(Collectors.toList())
    }

}