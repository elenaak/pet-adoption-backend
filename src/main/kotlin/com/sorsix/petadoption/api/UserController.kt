package com.sorsix.petadoption.api

import com.sorsix.petadoption.api.dto.*
import com.sorsix.petadoption.domain.PasswordResetToken
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.service.AuthService
import com.sorsix.petadoption.service.PasswordResetService
import com.sorsix.petadoption.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping
class UserController(val authService: AuthService,
                     val userService: UserService,
                     val passwordResetService: PasswordResetService) {

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

    @PostMapping("/login/forgot-password")
    fun forgotPassword(@RequestBody request: EmailPasswordResetRequest): ResponseEntity<Boolean> {
        return passwordResetService.resetPasswordViaEmail(request.email).map { response ->
            ResponseEntity.ok(response)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("/login/forgot-password/validate")
    fun validateResetPasswordToken(@RequestBody request: TokenValidationRequest): ResponseEntity<Boolean> {
        return passwordResetService.validateToken(request.token).map { response ->
            ResponseEntity.ok(response)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("/login/forgot-password/reset")
    fun resetPassword(@RequestBody request: ResetPasswordRequest):ResponseEntity<Boolean> {
        return passwordResetService.resetPassword(request.password,request.token).map { response ->
            ResponseEntity.ok(response)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/api/my-pets")
    fun getPetsByUser(): Set<Pet> {
        return userService.getPetsByUser()
    }
}