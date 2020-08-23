package com.sorsix.petadoption.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "pets")
class Pet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val type: String,

        @ManyToOne
        val owner: User,

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "contact_id", referencedColumnName = "id")
        val contact: Contact,

        val name: String,

        val breed: String,

        val color: String,

        val age: Int,

        @Enumerated(EnumType.STRING)
        val sex: Sex,

        val description: String,

        val behaviour: String,

        @Lob
        val image: String,

        @ManyToMany(mappedBy = "favoritePets")
        @JsonIgnoreProperties("favoritePets")
        val likes: Set<User> = HashSet(),

        val weight: Double,

        val height: Double,

        val allergies: String,

        val vaccination: String
)