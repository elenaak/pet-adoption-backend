package com.sorsix.petadoption.api

import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.service.FavouritePetService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/like")
class FavouritePetController(val favouritePetService: FavouritePetService) {


    @GetMapping("/{petId}")
    fun addOrDeletePetFromFavourite(@PathVariable petId: Long) {
        favouritePetService.addOrDeletePetFromFavourite(petId)
    }

    @GetMapping
    fun likedByUser(): List<Pet> {
        return favouritePetService.getLikedPets()
    }
}