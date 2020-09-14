package com.sorsix.petadoption.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


@Entity
@Table(name = "users")
data class User(
        @Id
        val username: String,

        @JsonIgnore
        var password: String,

        @ManyToOne
        val userRole: UserRole,

        var email: String,

        var description: String?,

        val active: Boolean = true

) {
    @OneToMany(
            mappedBy = "owner")
    @JsonIgnore
    val pets: MutableList<Pet> = ArrayList()

    @OneToMany(
            mappedBy = "author")
    @JsonIgnore
    val articles: MutableSet<Article> = HashSet()

    @ManyToMany
    @JoinTable(
            name = "user_favorites",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "pet_id")])
    @JsonIgnore
    var favoritePets: MutableList<Pet> = ArrayList()

    fun addToFavourite(pet: Pet) {
        favoritePets.add(pet)
    }

    fun deleteFromFavourite(pet: Pet) {
        favoritePets.remove(pet)
    }
}