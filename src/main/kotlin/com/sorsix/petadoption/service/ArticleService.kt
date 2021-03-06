package com.sorsix.petadoption.service

import com.sorsix.petadoption.api.dto.CreateArticleRequest
import com.sorsix.petadoption.domain.Article
import com.sorsix.petadoption.domain.exception.InvalidArticleIdException
import com.sorsix.petadoption.repository.ArticleRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArticleService(val articleRepository: ArticleRepository,
                     val userService: UserService) {

    val logger: Logger = LoggerFactory.getLogger(ArticleService::class.java)

    fun createArticle(request: CreateArticleRequest): Article {
        val author = userService.getCurrentUser()
        val article = Article(0, request.title, request.description, request.content, request.image,
                request.theme.toLowerCase(), author, LocalDateTime.now())
        logger.info("Saving article [{}]", article)
        return articleRepository.save(article)
    }

    fun getArticle(id: Long): Article {
        return articleRepository.findById(id).orElseThrow { InvalidArticleIdException() }
    }

    fun getAllByTheme(theme: String, page: Int, size: Int): Page<Article> {
        return articleRepository.getAllByThemeOrderByDateDesc(theme.toLowerCase(), PageRequest.of(page, size))
    }

}