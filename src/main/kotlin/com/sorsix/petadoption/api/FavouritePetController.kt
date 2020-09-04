package com.sorsix.petadoption.api

import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.service.FavouritePetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/like")
class FavouritePetController(val favouritePetService: FavouritePetService) {


    @GetMapping("/{petId}")
    fun addOrDeletePetFromFavourite(@PathVariable petId: Long) {
        favouritePetService.addOrDeletePetFromFavourite(petId)
    }

    @GetMapping
    fun likedBy(): Set<Pet> {
        return favouritePetService.getLikedPets()
    }

    @ExceptionHandler(InvalidPetIdException::class)
    fun petIdNotExistsHandler(e: InvalidPetIdException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Pet id not found"))
    }
}