package com.sorsix.petadoption.api.dto

import com.sorsix.petadoption.domain.Age
import com.sorsix.petadoption.domain.Contact
import com.sorsix.petadoption.domain.Sex

data class CreatePetRequest(
        val contact: Contact,
        val type: String,
        val name: String,
        val breed: String,
        val color: String,
        val age: Age,
        val sex: Sex,
        val description: String,
        val behaviour: String,
        val image: String,
        val weight: Double,
        val height: Double,
        val allergies: String,
        val vaccines: String,
        val neutered: Boolean
)