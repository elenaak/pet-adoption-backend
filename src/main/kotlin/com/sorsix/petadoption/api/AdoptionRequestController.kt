package com.sorsix.petadoption.api

import com.sorsix.petadoption.domain.AdoptionRequest
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.service.AdoptionRequestService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/adopt")
class AdoptionRequestController(val service: AdoptionRequestService) {

    @PostMapping("/{petId}")
    fun createRequest(@PathVariable petId: Long) {
        service.createAdoptionRequest(petId)
    }

    @GetMapping("/requests/{id}")
    fun getRequestsByPetId(@PathVariable id: Long): List<User> {
        return service.getRequestsByPetId(id)
    }
}