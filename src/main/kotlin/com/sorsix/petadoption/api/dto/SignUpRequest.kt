package com.sorsix.petadoption.api.dto

data class SignUpRequest(val username: String, var password: String, val email: String, val description: String?)