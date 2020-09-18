package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Contact
import com.sorsix.petadoption.repository.ContactRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ContactService(private val contactRepository: ContactRepository) {

    val logger: Logger = LoggerFactory.getLogger(ContactService::class.java)

    fun createContact(email: String, name: String, lastName: String,
                      address: String, city: String, telephone: String): Contact {
        val contact = Contact(0, email, name, lastName, address, city, telephone)
        logger.info("Saving contact [{}]", contact)
        return contactRepository.save(contact)
    }
}