package com.sorsix.petadoption.api

import com.sorsix.petadoption.service.AdoptionRequestService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/adopt")
class AdoptionRequestController(val service: AdoptionRequestService) {

    @PostMapping("/{petId}")
    fun createRequest(@PathVariable petId: Long) {
        service.createAdoptionRequest(petId)
    }
}