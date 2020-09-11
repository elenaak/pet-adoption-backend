package com.sorsix.petadoption.api

import com.sorsix.petadoption.api.dto.CreateArticleRequest
import com.sorsix.petadoption.domain.Article
import com.sorsix.petadoption.service.ArticleService
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/article")
class ArticleController(val articleService: ArticleService) {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    fun createArticle(@RequestBody request: CreateArticleRequest): Article {
        return articleService.createArticle(request)
    }

    @GetMapping
    fun getAll(): List<Article> {
        return articleService.getAll()
    }

    @GetMapping("/search")
    fun getAllByTheme(@RequestParam theme: String, @RequestParam page: Int, @RequestParam size: Int): Page<Article> {
        return articleService.getAllByTheme(theme, page, size)
    }

    @GetMapping("/{id}")
    fun getArticle(@PathVariable id: Long): Article {
        return articleService.getArticle(id)
    }
}