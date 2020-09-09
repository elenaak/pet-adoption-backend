package com.sorsix.petadoption.api

import com.sorsix.petadoption.api.dto.ChangePasswordRequest
import com.sorsix.petadoption.api.dto.EditUserRequest
import com.sorsix.petadoption.api.dto.SignUpRequest
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.service.AuthService
import com.sorsix.petadoption.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping
class UserController(val authService: AuthService, val userService: UserService) {

    @PostMapping("/authenticate/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): User {
        return authService.registerUser(signUpRequest)
    }

    @PostMapping("edit/profile")
    fun editUser(@RequestBody request: EditUserRequest) {
        return userService.editUser(request.email, request.description)
    }

    @GetMapping("/authenticate")
    fun getCurrentUser(): User {
        return userService.getUserById(authService.getCurrentUserId())
    }

    @PostMapping("edit/profile/password")
    fun changePassword(@RequestBody request: ChangePasswordRequest) {
        return authService.changePassword(request.oldPassword, request.newPassword)
    }

    @GetMapping("/api/my-pets")
    fun getPetsByUser(): Set<Pet> {
        return userService.getPetsByUser()
    }

}