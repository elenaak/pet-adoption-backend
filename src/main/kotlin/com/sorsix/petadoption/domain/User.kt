package com.sorsix.petadoption.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*


@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val username: String,

        //@JsonIgnore
        val password: String,

        @ManyToMany
        @JoinTable(
                name = "user_favorites",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "pet_id")])
        @JsonIgnoreProperties("likes")
        val favoritePets: Set<Pet> = HashSet(),

        @OneToMany(
                mappedBy = "owner")
        @JsonIgnore
        val pets: Set<Pet> = HashSet()
)
