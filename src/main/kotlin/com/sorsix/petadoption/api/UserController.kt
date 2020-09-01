package com.sorsix.petadoption.api

import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.domain.UserDetails
import com.sorsix.petadoption.domain.exception.UsernameAlreadyExists
import com.sorsix.petadoption.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
class UserController(val authService: AuthService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): User {
        return authService.registerUser(signUpRequest)
    }

    @GetMapping
    fun getCurrentUser(): UserDetails {
        return authService.getCurrentUser()
    }

    @ExceptionHandler(UsernameAlreadyExists::class)
    fun usernameExistsHandler(e: UsernameAlreadyExists): ResponseEntity<Map<String, String>> {
        return ResponseEntity.badRequest().body(mapOf("error" to e.toString()))
    }

}