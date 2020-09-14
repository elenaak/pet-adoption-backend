package com.sorsix.petadoption.api.dto

data class CreateArticleRequest(
        val title: String,
        val description: String,
        val content: String,
        val image: String?,
        val theme: String
)