package com.sorsix.petadoption.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="user_roles")
class UserRole(
        @Id
        val name: String
)