package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.*
import com.sorsix.petadoption.domain.exception.UnauthorizedException
import com.sorsix.petadoption.repository.PetRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

internal class PetServiceTest {

    @RelaxedMockK
    lateinit var petRepository: PetRepository

    @RelaxedMockK
    lateinit var contactService: ContactService

    @RelaxedMockK
    lateinit var userService: UserService

    private lateinit var petService: PetService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        petService = PetService(petRepository, contactService, userService)
    }

    @Test
    fun `should throw exception if non-owner try to edit pet`() {
        val contact = Contact(0, "email", "firstname", "lastname", "address", "city"
                , "tel")
        val role = UserRole("ROLE_USER")
        val user = User("otherusername", "pasw", role, "email", null)
        val pet = Pet(0, "type", user, contact, "name", "breed", "color", Age.Adult, Sex.Female
                , "desc", "beh", null, 1.0, 2.0, "none", "all",
                LocalDateTime.now())

        every { userService.getCurrentUser() } returns User("username", "pasw", role,
                "email", null)
        every { petRepository.findById(0) } returns Optional.of(pet)


        assertThrows<UnauthorizedException> {
            petService.editPet(0, "type", "name", "breed", "color", Age.Adult,
                    Sex.Female, "desc", "beh", null, 1.0, 2.0, "none",
                    "all", "email", "firstname", "lastname", "address", "city"
                    , "tel")
        }
    }

    @Test
    fun `should throw exception if non-owner try to delete pet`() {
        val contact = Contact(0, "email", "firstname", "lastname", "address", "city"
                , "tel")
        val role = UserRole("ROLE_USER")
        val user = User("otherusername", "pasw", role, "email", null)
        val pet = Pet(0, "type", user, contact, "name", "breed", "color", Age.Adult, Sex.Female
                , "desc", "beh", null, 1.0, 2.0, "none", "all",
                LocalDateTime.now())

        every { userService.getCurrentUser() } returns User("username", "pasw", role,
                "email", null)
        every { petRepository.findById(0) } returns Optional.of(pet)


        assertThrows<UnauthorizedException> {
            petService.deletePet(0)
        }
    }
}