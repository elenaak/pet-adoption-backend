package com.sorsix.petadoption.api

import com.sorsix.petadoption.api.dto.*
import com.sorsix.petadoption.domain.Article
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.User
import com.sorsix.petadoption.service.AuthService
import com.sorsix.petadoption.service.PasswordResetService
import com.sorsix.petadoption.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
@CrossOrigin
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
    fun forgotPassword(@RequestBody request: EmailPasswordResetRequest): Boolean {
        return passwordResetService.resetPasswordViaEmail(request.email)
    }

    @PostMapping("/login/forgot-password/validate")
    fun validateResetPasswordToken(@RequestBody request: TokenValidationRequest): Boolean {
        return passwordResetService.validateToken(request.token)
    }

    @PostMapping("/login/forgot-password/reset")
    fun resetPassword(@RequestBody request: ResetPasswordRequest): Boolean {
        return passwordResetService.resetPassword(request.password, request.token)
    }

    @GetMapping("/api/my-pets")
    fun getPetsByUser(): List<Pet> {
        return userService.getPetsByUser()
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/my-articles")
    fun getArticlesByUser(): List<Article> {
        return userService.getArticlesByAuthor()
    }
}