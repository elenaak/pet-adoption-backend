package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.Sex
import com.sorsix.petadoption.repository.PetRepository
import com.sorsix.petadoption.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@Service
class PetService(val petRepository: PetRepository,
                 val contactService: ContactService,
                 val userRepository: UserRepository) {

    fun findAll(page: Int?, size: Int?): Page<Pet> {
        val p = page ?: 0
        val s = size ?: 2
        val pageable: Pageable = PageRequest.of(p, s)
        return petRepository.findAllByOrderByTimestampDesc(pageable)
    }

    fun createPet(type: String, firstName: String, breed: String, color: String, age: Int, sex: Sex,
                  description: String, behaviour: String, image: MultipartFile?, weight: Double, height: Double,
                  allergies: String, vaccination: String,
                  email: String, name: String, lastName: String, address: String, city: String, telephone: String) {

        val contact = contactService.createContact(email, name, lastName, address, city, telephone)
        var base64Image: String? = null
        if (image != null && image.name.isNotEmpty()) {
            val bytes = image.bytes
            base64Image = java.lang.String.format("data:%s;base64,%s", image.contentType,
                    Base64.getEncoder().encodeToString(bytes))
        }
        val user = userRepository.findById("username").orElseThrow()
        val pet = Pet(0, type, user, contact, firstName, breed, color, age, sex, description, behaviour, base64Image,
                weight, height, allergies, vaccination, LocalDateTime.now())
    }

    fun deletePet(id: Long): Optional<Unit> {
        return if (petRepository.existsById(id))
            Optional.of(petRepository.deleteById(id))
        else
            Optional.empty()
    }

    fun getPetById(id: Long): Optional<Pet> {
        return petRepository.findById(id)
    }

}