package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Age
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.Sex
import com.sorsix.petadoption.repository.PetRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PetSearchByFiltersService(val petRepository: PetRepository) {

    fun getPetsUsingFilters(name: String?, type: String?, breed: String?, age: Age?, sex: Sex?, color: String?,
                            page: Int?, size: Int?): Page<Pet> {
        val p = page ?: 0
        val s = size ?: 10
        return petRepository.findByFilters(name, type, breed, age, sex, color, PageRequest.of(p, s))
    }
}