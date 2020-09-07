package com.sorsix.petadoption.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*


@Entity
@Table(name = "users")
data class User(
        @Id
        val username: String,

        //@JsonIgnore
        val password: String,

        @ManyToOne
        val userRole: UserRole,

        var email: String,

        var description: String?,

        val active: Boolean = true

) {
    @OneToMany(
            mappedBy = "owner")
    @JsonIgnore
    val pets: MutableSet<Pet> = HashSet()

    @ManyToMany
    @JoinTable(
            name = "user_favorites",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "pet_id")])
    @JsonIgnore()
    var favoritePets: MutableSet<Pet> = HashSet()

    fun addToFavourite(pet: Pet) {
        favoritePets.add(pet)
    }

    fun deleteFromFavourite(pet: Pet) {
        favoritePets.remove(pet)
    }
}