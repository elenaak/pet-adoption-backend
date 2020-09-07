package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.AdoptionRequest
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.repository.AdoptionRequestRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import java.util.stream.Collectors

@Service
class AdoptionRequestService(val petService: PetService,
                             val authService: AuthService,
                             val userService: UserService,
                             val repository: AdoptionRequestRepository,
                             val emailService: EmailService) {


    fun createAdoptionRequest(petId: Long) {
        val pet = petService.getPetById(petId)
        val receiver = pet.contact
        val adopter = userService.getUserById(authService.getCurrentUserId())
        emailService.sendEmail(pet, receiver, adopter)
        repository.save(AdoptionRequest(0, pet.owner, adopter, pet, LocalDateTime.now()))
    }

    fun getRequestsByPetId(petId: Long): List<User> {
        return repository.findAdoptionRequestByPetIdOrderByTimeDesc(petId)
                .stream().map { it.adopter }.collect(Collectors.toList())
    }

    @ExceptionHandler(InvalidPetIdException::class)
    fun petIdNotExistsHandler(e: InvalidPetIdException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Pet id not found"))
    }
}