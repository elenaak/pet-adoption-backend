package com.sorsix.petadoption.api

import com.sorsix.petadoption.domain.UserDetails
import com.sorsix.petadoption.service.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authenticate")
class UserController(val authService: AuthService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest) {
        authService.registerUser(signUpRequest)
    }

    @GetMapping
    fun getCurrentUser(): UserDetails {
        return authService.getCurrentUser()
    }
}