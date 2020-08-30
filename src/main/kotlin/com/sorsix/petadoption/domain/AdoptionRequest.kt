package com.sorsix.petadoption.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "adoption_requests")
data class AdoptionRequest(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        val owner: User,

        @ManyToOne(fetch = FetchType.LAZY)
        val adopter: User,

        @ManyToOne(fetch = FetchType.LAZY)
        val pet: Pet,

        val time: LocalDateTime
)