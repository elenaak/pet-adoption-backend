package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class FavouritePetService(val petRepository: PetRepository,
                          val userService: UserService) {

    fun addOrDeletePetFromFavourite(petId: Long) {
        val pet = petRepository.findById(petId).orElseThrow { throw InvalidPetIdException() }
        val user = userService.getCurrentUser()
        if (user.favoritePets.contains(pet))
            deleteFromFavourite(user, pet)
        else
            addToFavourite(user, pet)
        petRepository.save(pet)
        userService.saveUser(user)
    }

    private fun addToFavourite(user: User, pet: Pet) {
        user.addToFavourite(pet)
        pet.like(user)
    }

    private fun deleteFromFavourite(user: User, pet: Pet) {
        user.deleteFromFavourite(pet)
        pet.unlike(user)
    }

    fun getLikedPets(): List<Pet> {
        val user = userService.getCurrentUser()
        return user.favoritePets
    }

}