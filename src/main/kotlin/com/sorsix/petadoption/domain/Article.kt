package com.sorsix.petadoption.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "articles")
data class Article(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val title: String,

        val description: String,

        @Lob
        val content: String,

        @Lob
        val image: String?,

        val theme: String,

        @ManyToOne(fetch = FetchType.LAZY)
        val author: User,

        val date: LocalDateTime

)