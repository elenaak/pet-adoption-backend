package com.sorsix.petadoption.api

import com.sorsix.petadoption.domain.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExists::class)
    fun usernameExistsHandler(e: UsernameAlreadyExists): ResponseEntity<Map<String, String>> {
        return ResponseEntity.badRequest().body(mapOf("error" to e.toString()))
    }

    @ExceptionHandler(InvalidUserIdException::class)
    fun usernameNotExistsHandler(e: InvalidUserIdException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Username not found"))
    }

    @ExceptionHandler(PasswordsNotTheSameException::class)
    fun passwordsNotSame(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Old password is not correct"))
    }

    @ExceptionHandler(InvalidPetIdException::class)
    fun petIdNotExistsHandler(e: InvalidPetIdException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Pet id not found"))
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorizedExceptionHandler(e: UnauthorizedException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "You are not allowed"))
    }
}