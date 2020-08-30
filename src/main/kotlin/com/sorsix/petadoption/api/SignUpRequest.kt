package com.sorsix.petadoption.api

data class SignUpRequest(val username: String, var password: String, val email: String) {
}