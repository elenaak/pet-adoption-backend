package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.repository.PetRepository
import com.sorsix.petadoption.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class FavouritePetService(val authService: AuthService,
                          val petRepository: PetRepository,
                          val userRepository: UserRepository) {

    fun addOrDeletePetFromFavourite(petId: Long) {
        val pet = petRepository.findById(petId).orElseThrow { throw InvalidPetIdException() }
        val user = userRepository.findById(authService.getCurrentUserId()).orElseThrow { throw InvalidUserIdException() }
        if (user.favoritePets.contains(pet))
            deleteFromFavourite(user, pet)
        else
            addToFavourite(user, pet)
        petRepository.save(pet)
        userRepository.save(user)
    }

    private fun addToFavourite(user: User, pet: Pet) {
        user.addToFavourite(pet)
        pet.like(user)
    }

    private fun deleteFromFavourite(user: User, pet: Pet) {
        user.deleteFromFavourite(pet)
        pet.unlike(user)
    }

    fun getLikedPets(): Set<Pet> {
        val user = userRepository.findById(authService.getCurrentUserId()).orElseThrow { throw InvalidUserIdException() }
        return user.favoritePets
    }

}