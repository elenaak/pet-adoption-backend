package com.sorsix.petadoption.api

import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.Sex
import com.sorsix.petadoption.service.PetSearchByFiltersService
import com.sorsix.petadoption.service.PetService
import org.springframework.data.domain.Page
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
    fun searchPets(name: String?, type: String?, breed: String?, age: Int?,
                   sex: Sex?, color: String?, page: Int?, size: Int?): Page<Pet> {
        return petSearchService.getPetsUsingFilters(name, type, breed, age, sex, color, page, size)
    }

    @GetMapping("/{id}")
    fun getPetById(@PathVariable id: Long): ResponseEntity<Pet> {
        return petService.getPetById(id).map { pet ->
            ResponseEntity.ok(pet)
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deletePet(@PathVariable id: Long): ResponseEntity<Unit> {
        return petService.deletePet(id).map { res ->
            ResponseEntity.ok(res)
        }.orElse(ResponseEntity.notFound().build())
    }
}