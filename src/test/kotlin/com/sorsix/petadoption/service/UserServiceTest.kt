package com.sorsix.petadoption.service

import com.sorsix.petadoption.domain.*
import com.sorsix.petadoption.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

internal class UserServiceTest {
    @RelaxedMockK
    lateinit var userRepository: UserRepository

    @RelaxedMockK
    lateinit var authService: AuthService

    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        userService = UserService(userRepository, authService)
    }

    @Test
    fun `should return list of user's pets`() {
        val role = UserRole("ROLE_USER")
        val user = User("username", "pasw", role, "email", null)
        val contact = Contact(0, "email", "firstname", "lastname", "address", "city"
                , "tel")
        val pet = Pet(0, "type", user, contact, "name", "breed", "color", Age.Adult, Sex.Female
                , "desc", "beh", null, 1.0, 2.0, "none", "all",
                LocalDateTime.now())
        user.pets.add(pet)
        every { authService.getCurrentUserId() } returns "username"
        every { userRepository.findById("username") } returns Optional.of(user)

        val expected: MutableList<Pet> = ArrayList()
        expected.add(pet)

        assertEquals(expected, userService.getPetsByUser())
    }

    @Test
    fun `should return sorted articles`() {
        val role = UserRole("ROLE_USER")
        val user = User("username", "pasw", role, "email", null)
        every { authService.getCurrentUserId() } returns "username"
        every { userRepository.findById("username") } returns Optional.of(user)
        val article1 = Article(0, "title", "desc", "content", null, "theme", user,
                LocalDateTime.now())
        val article2 = Article(1, "title", "desc", "content", null, "theme", user,
                LocalDateTime.now().plusSeconds(10))
        user.articles.add(article1)
        user.articles.add(article2)

        val expected: MutableList<Article> = ArrayList()
        expected.add(article2)
        expected.add(article1)
        val actual = userService.getArticlesByAuthor()

        assertEquals(expected, actual)

    }
}