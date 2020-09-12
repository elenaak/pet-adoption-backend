package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.Contact
import com.sorsix.petadoption.domain.PasswordResetToken
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.File
import javax.mail.internet.InternetAddress

@Service
class EmailService(val mailSender: JavaMailSender) {

    @Async
    fun sendEmailToOwner(pet: Pet, receiver: Contact, adopter: User) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(InternetAddress(receiver.email))
        helper.setSubject("Notification from PetFriends")
        helper.setFrom("petfriendsmk@gmail.com")
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

    @Async
    fun sendWelcomeEmail(receiver: User) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(InternetAddress(receiver.email))
        helper.setSubject("Welcome to  PetFriends")
        helper.setFrom("petfriendsmk@gmail.com")
        helper.setText(createWelcomeMessage(receiver.username), false)
        val logo = FileSystemResource(File("static/logosmall.png"))
        helper.addInline("logo", logo)
        mailSender.send(message)
    }

    private fun createWelcomeMessage(username: String): String {
        val message = StringBuilder()
        message.append("Dear ").append(username).append(" ").append("\n")
                .append("Thanks for joining PetFriends! ").append(".\n")
                .append("PetFriends is a simple platform where you can find and adopt a pet or you can post " +
                        "an add and find an adopter for your pet").append(".\n")
                .append("We are glad to have you as a member :)").append(".\n")
                .append("Warm regards from team of PetFriends")
        return message.toString()
    }

    @Async
    fun sendPasswordResetEmail(passwordResetToken: PasswordResetToken) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(InternetAddress(passwordResetToken.user.email))
        helper.setSubject("Reset your password")
        helper.setFrom("petfriendsmk@gmail.com")
        helper.setText(createPasswordResetMessage(passwordResetToken), false)
        val logo = FileSystemResource(File("static/logosmall.png"))
        helper.addInline("logo", logo)
        mailSender.send(message)
    }

    private fun createPasswordResetMessage(passwordResetToken: PasswordResetToken): String {
        val message = StringBuilder()
        message.append("Dear ").append(passwordResetToken.user.username).append("\n")
                .append("Need to reset your password? No problem!")
                .append(" Just click the link below and you'll be on your way.")
                .append(" If you did not make this request, please ignore the email.").append("\n")
                .append("Link to reset your password:").append("\n")
                .append("http://localhost:4200/login/forgot-password/reset?token=")
                .append(passwordResetToken.token)

        return message.toString()
    }

}