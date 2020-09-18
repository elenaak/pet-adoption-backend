package com.sorsix.petadoption.domain

import com.fasterxml.jackson.annotation.JsonIgnore
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

        @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "contact_id", referencedColumnName = "id")
        val contact: Contact,

        val name: String,

        val breed: String,

        val color: String,

        @Enumerated(EnumType.STRING)
        val age: Age,

        @Enumerated(EnumType.STRING)
        val sex: Sex,

        @Lob
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
    @JsonIgnore
    val likes: MutableSet<User> = HashSet()

    fun like(user: User) {
        likes.add(user)
    }

    fun unlike(user: User) {
        likes.remove(user)
    }

    override fun toString(): String {
        return "Pet(id=$id, type='$type', name='$name', breed='$breed', color='$color', age=$age, sex=$sex, " +
                "behaviour='$behaviour', weight=$weight, height=$height, allergies='$allergies', " +
                "vaccination='$vaccination', timestamp=$timestamp)"
    }


}