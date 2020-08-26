package com.sorsix.petadoption.domain

import javax.persistence.*

@Entity
@Table(name = "contacts")
data class Contact(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val email: String,

        val firstName: String,

        val lastName: String,

        val address: String,

        val city: String,

        val telephone: String

)