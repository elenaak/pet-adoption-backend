package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.AdoptionRequest
import com.sorsix.petadoption.domain.Contact
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.exception.InvalidPetIdException
import com.sorsix.petadoption.repository.AdoptionRequestRepository
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime
import javax.mail.internet.InternetAddress

@Service
class AdoptionRequestService(val petService: PetService,
                             val authService: AuthService,
                             val userService: UserService,
                             val mailSender: JavaMailSender,
                             val repository: AdoptionRequestRepository) {


    fun createAdoptionRequest(petId: Long) {
        val pet = petService.getPetById(petId).orElseThrow { throw InvalidPetIdException() }
        val receiver = pet.contact
        val adopter = userService.getUserById(authService.getCurrentUserId())
        sendEmail(pet, receiver, adopter)
        repository.save(AdoptionRequest(0, pet.owner, adopter, pet, LocalDateTime.now()))
    }

    private fun sendEmail(pet: Pet, receiver: Contact, adopter: User) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(InternetAddress(receiver.email))
        helper.setSubject("Notification from PetFriends")
        helper.setFrom("adoptapetapp@gmail.com")
        helper.setText(createMessageContent(pet, receiver, adopter), false)
        val logo = FileSystemResource(File("static/logosmall.png"))
        helper.addInline("logo", logo)
        mailSender.send(message)
    }

    private fun createMessageContent(pet: Pet, receiver: Contact, adopter: User): String {
        val message = StringBuilder()
        message.append("Dear ").append(receiver.firstName).append(" ").append(receiver.lastName).append(",\n")
                .append("We would like to inform you that ").append(adopter.username)
                .append(" is interested about your pet ").append(pet.name).append(".\n")
                .append("You can contact this user on this email ").append(adopter.email).append(".\n")
                .append("Warm regards from team of PetFriends")
        return message.toString()
    }
}