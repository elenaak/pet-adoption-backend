package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun getAllByThemeOrderByDateDesc(theme: String, p:Pageable): Page<Article>
}