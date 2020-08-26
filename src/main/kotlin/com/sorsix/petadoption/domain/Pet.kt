package com.sorsix.petadoption.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "pets")
data class Pet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val type: String,

        @ManyToOne(fetch = FetchType.LAZY)
        val owner: User,

        @OneToOne(cascade = [CascadeType.ALL])
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
        val image: String?,

        val weight: Double,

        val height: Double,

        val allergies: String,

        val vaccination: String,

        @JsonIgnore
        val timestamp: LocalDateTime
) {
    @ManyToMany(mappedBy = "favoritePets")
    @JsonIgnoreProperties("favoritePets")
    val likes: MutableSet<User> = HashSet()

    fun like(user: User){
        likes.add(user)
    }
    fun unlike(user: User){
        likes.remove(user)
    }
}