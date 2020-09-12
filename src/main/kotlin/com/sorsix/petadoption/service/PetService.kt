package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Age
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.Sex
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.domain.exception.UnauthorizedException
import com.sorsix.petadoption.repository.PetRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PetService(val petRepository: PetRepository,
                 val contactService: ContactService,
                 val userService: UserService,
                 val authService: AuthService) {

    fun findAll(page: Int?, size: Int?): Page<Pet> {
        val p = page ?: 0
        val s = size ?: 2
        val pageable: Pageable = PageRequest.of(p, s)
        return petRepository.findAllByOrderByTimestampDesc(pageable)
    }

    fun createPet(type: String, name: String, breed: String, color: String, age: Age, sex: Sex,
                  description: String, behaviour: String, image64Base: String?, weight: Double, height: Double,
                  allergies: String, vaccination: String,
                  email: String, firstName: String, lastName: String, address: String, city: String,
                  telephone: String): Pet {
        val contact = contactService.createContact(email, name, lastName, address, city, telephone)
        val user = userService.getUserById(authService.getCurrentUserId())
        return petRepository.save(Pet(0, type, user, contact, firstName, breed, color, age, sex, description,
                behaviour, image64Base, weight, height, allergies, vaccination, LocalDateTime.now()))
    }

    fun deletePet(id: Long) {
        return this.petRepository.findById(id).map {
            val user = userService.getUserById(authService.getCurrentUserId())
            if (user.username != it.owner.username)
                throw UnauthorizedException()
            for (u: User in userService.findAll()) {
                u.deleteFromFavourite(it)
                u.pets.remove(it)
            }
            petRepository.delete(it)
        }.orElseThrow { throw InvalidPetIdException() }
    }

    fun getPetById(id: Long): Pet {
        return petRepository.findById(id).orElseThrow { throw InvalidPetIdException() }
    }

    fun editPet(id: Long, type: String, name: String, breed: String, color: String, age: Age, sex: Sex,
                description: String, behaviour: String, image64Base: String?, weight: Double, height: Double,
                allergies: String, vaccination: String,
                email: String, firstName: String, lastName: String, address: String, city: String,
                telephone: String): Pet {
        return this.petRepository.findById(id).map {
            val user = userService.getUserById(authService.getCurrentUserId())
            if (user.username != it.owner.username)
                throw UnauthorizedException()
            val contact = contactService.createContact(email, name, lastName, address, city, telephone)
            val updated = Pet(id, type, user, contact, firstName, breed, color, age, sex, description,
                    behaviour, image64Base, weight, height, allergies, vaccination, LocalDateTime.now())
            petRepository.save(updated)
            updated
        }.orElseThrow { throw InvalidPetIdException() }
    }

}