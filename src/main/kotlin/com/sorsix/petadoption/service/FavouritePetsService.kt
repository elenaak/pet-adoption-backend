package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.repository.PetRepository
import com.sorsix.petadoption.repository.UserRepository

class FavouritePetsService(val petRepository: PetRepository, val userRepository: UserRepository) {

    fun likePet(petId: Long, userId: Long) {//ova treba da e id na momentalno najaveniot korisnik
        val user = userRepository.findById(userId).orElseThrow { InvalidUserIdException() }
        val pet = petRepository.findById(petId).orElseThrow { InvalidPetIdException() }
        user.likePet(pet)
        pet.like(user)
        petRepository.save(pet)
        userRepository.save(user)
    }
    fun unlikePet(petId: Long, userId: Long) {//ova treba da e id na momentalno najaveniot korisnik
        val user = userRepository.findById(userId).orElseThrow { InvalidUserIdException() }
        val pet = petRepository.findById(petId).orElseThrow { InvalidPetIdException() }
        user.unlikePet(pet)
        pet.unlike(user)
        petRepository.save(pet)
        userRepository.save(user)
    }
}