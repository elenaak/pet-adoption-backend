package com.sorsix.petadoption.config

class SecurityConstants {

    companion object {
        const val SECRET: String = "s3cr3tt0k3n"
        const val EXPIRATION_TIME: Int = 864_000_000
        const val TOKEN_PREFIX: String = "Bearer "
        const val HEADER_STRING: String = "Authorization"
    }
}