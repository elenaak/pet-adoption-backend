package com.sorsix.petadoption.api

import com.sorsix.petadoption.domain.UserDetails
import com.sorsix.petadoption.service.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authenticate")
class UserController(val authService: AuthService) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody username: String, @RequestBody password: String, @RequestBody email: String) {
        authService.registerUser(username, password, email)
    }

    @GetMapping
    fun getCurrentUser(): UserDetails {
        return authService.getCurrentUser()
    }
}