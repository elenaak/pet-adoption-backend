package com.sorsix.petadoption.api

import com.sorsix.petadoption.service.AdoptionRequestService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/adopt")
class AdoptionRequestController(val service: AdoptionRequestService){

    @PostMapping("/{petId}")
    fun createRequest(@PathVariable petId: Long){
        service.createAdoptionRequest(petId)
    }
}