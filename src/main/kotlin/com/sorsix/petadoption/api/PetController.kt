package com.sorsix.petadoption.api

import com.sorsix.petadoption.api.dto.CreatePetRequest
import com.sorsix.petadoption.domain.Age
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.Sex
import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.domain.exception.UnauthorizedException
import com.sorsix.petadoption.service.PetSearchByFiltersService
import com.sorsix.petadoption.service.PetService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/pets")
class PetController(val petSearchService: PetSearchByFiltersService,
                    val petService: PetService) {

    @GetMapping
    fun getAll(@RequestParam page: Int?, @RequestParam size: Int?): Page<Pet> {
        return petService.findAll(page, size)
    }

    @GetMapping("/search")
    fun searchPets(name: String?, type: String?, breed: String?, age: Age?,
                   sex: Sex?, color: String?, page: Int?, size: Int?): Page<Pet> {
        return petSearchService.getPetsUsingFilters(name, type, breed, age, sex, color, page, size)
    }

    @GetMapping("/{id}")
    fun getPetById(@PathVariable id: Long): Pet {
        return petService.getPetById(id)
    }

    @DeleteMapping("/{id}")
    fun deletePet(@PathVariable id: Long) {
        return petService.deletePet(id)
    }

    @PostMapping("/create")
    fun createPet(@RequestBody request: CreatePetRequest): Pet {
        return with(request) {
            petService.createPet(type, name, breed, color, age, sex, description, behaviour, image, weight, height,
                    allergies, vaccines, contact.email, contact.firstName, contact.lastName, contact.address, contact.city,
                    contact.telephone)
        }
    }

    @PutMapping("/edit/{id}")
    fun editPet(@PathVariable id: Long, @RequestBody r: CreatePetRequest): Pet {
        return petService.editPet(id, r.type, r.name, r.breed, r.color, r.age, r.sex, r.description, r.behaviour, r.image,
                r.weight, r.height, r.allergies, r.vaccines, r.contact.email, r.contact.firstName, r.contact.lastName,
                r.contact.address, r.contact.city, r.contact.telephone)
    }

    @ExceptionHandler(InvalidPetIdException::class)
    fun petIdNotExistsHandler(e: InvalidPetIdException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Pet id not found"))
    }

    @ExceptionHandler(InvalidUserIdException::class)
    fun usernameNotExistsHandler(e: InvalidUserIdException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Username not found"))
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorizedExceptionHandler(e: UnauthorizedException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "You are not allowed"))
    }
}