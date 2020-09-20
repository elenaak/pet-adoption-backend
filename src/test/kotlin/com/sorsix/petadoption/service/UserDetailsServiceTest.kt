package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.UserRole
import com.sorsix.petadoption.domain.exception.UsernameAlreadyExists
import com.sorsix.petadoption.repository.UserRepository
import com.sorsix.petadoption.repository.UserRoleRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*


internal class UserDetailsServiceTest {
    @RelaxedMockK
    lateinit var userRepository: UserRepository

    @RelaxedMockK
    lateinit var roleRepository: UserRoleRepository

    @RelaxedMockK
    lateinit var emailService: EmailService

    private lateinit var userService: UserDetailsService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        userService = UserDetailsService(userRepository, roleRepository, emailService)
    }

    @Test
    fun `register should throw exception if username exists`() {
        every { userRepository.existsById("username") } returns true

        assertThrows<UsernameAlreadyExists> {
            userService.register("username", "psw", "email", "desc")
        }
    }

    @Test
    fun `change password`() {
        val role = UserRole("ROLE_USER")
        val user = User("username", "pasw", role, "email", null)
        every { userRepository.findById("username") } returns Optional.of(user)
        user.password = "newPassword"
        every { userRepository.save(user) } returns user

        assertEquals(user, userService.changePassword("username", "newPassword"))
    }
}