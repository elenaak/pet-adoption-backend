package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Contact
import com.sorsix.petadoption.repository.ContactRepository
import org.springframework.stereotype.Service

@Service
class ContactService(private val contactRepository: ContactRepository) {

    fun createContact(email: String, name: String, lastName: String,
                      address: String, city: String, telephone: String): Contact {
        val c = Contact(0, email, name, lastName, address, city, telephone)
        return contactRepository.save(c)
    }
}