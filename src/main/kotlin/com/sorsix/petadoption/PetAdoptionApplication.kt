package com.sorsix.petadoption

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class PetAdoptionApplication

fun main(args: Array<String>) {
    runApplication<PetAdoptionApplication>(*args)
}
