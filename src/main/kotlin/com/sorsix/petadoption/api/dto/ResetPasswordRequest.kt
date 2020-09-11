package com.sorsix.petadoption.api.dto

data class ResetPasswordRequest(val token: String, val password: String)