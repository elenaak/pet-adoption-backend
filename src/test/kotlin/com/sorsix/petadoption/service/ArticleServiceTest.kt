package com.sorsix.petadoption.service

import com.sorsix.petadoption.api.dto.CreateArticleRequest
import com.sorsix.petadoption.domain.exception.InvalidUserIdException
import com.sorsix.petadoption.repository.ArticleRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ArticleServiceTest {
    @RelaxedMockK
    lateinit var repository: ArticleRepository

    @RelaxedMockK
    lateinit var userService: UserService

    private lateinit var articleService: ArticleService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        articleService = ArticleService(repository, userService)
    }

    @Test
    fun `createArticle should throw exception if user is not authenticated`() {

        val articleRequest = CreateArticleRequest("title", "desc", "content", null,
                "theme")

        every { userService.getCurrentUser() } throws InvalidUserIdException()

        assertThrows<InvalidUserIdException> {
            articleService.createArticle(request = articleRequest)
        }
    }

}