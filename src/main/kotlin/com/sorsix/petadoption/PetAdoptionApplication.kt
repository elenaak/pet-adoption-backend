package com.sorsix.petadoption

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetAdoptionApplication

fun main(args: Array<String>) {
    runApplication<PetAdoptionApplication>(*args)
}
