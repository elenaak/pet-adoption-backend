package com.sorsix.petadoption.api.dto

data class ChangePasswordRequest(val oldPassword: String, val newPassword: String)