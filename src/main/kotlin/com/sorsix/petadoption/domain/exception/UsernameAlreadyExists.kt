package com.sorsix.petadoption.domain.exception

class UsernameAlreadyExists(val username: String) : Exception() {
    override fun toString(): String {
        return "Username $username already exists"
    }
}