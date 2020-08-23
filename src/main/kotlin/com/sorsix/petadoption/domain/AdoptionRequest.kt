package com.sorsix.petadoption.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="adoption_requests")
data class AdoptionRequest(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @ManyToOne
        val user: User,

        @ManyToOne
        val pet: Pet,

        val time: LocalDateTime,

        val confirmed: Boolean
)