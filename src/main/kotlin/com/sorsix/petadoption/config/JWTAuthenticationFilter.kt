package com.sorsix.petadoption.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sorsix.petadoption.domain.exception.PasswordsNotTheSameException
import com.sorsix.petadoption.domain.exception.UserNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthenticationFilter(
        val authenticationMng: AuthenticationManager,
        val userDetailsService: UserDetailsService,
        val passwordEncoder: PasswordEncoder
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val creds = jacksonObjectMapper().readValue(request.inputStream, AccountCredentials::class.java)
            val user: UserDetails
            try {
                user = userDetailsService.loadUserByUsername(creds.username)
                if (!passwordEncoder.matches(creds.password, user.password))
                    throw PasswordsNotTheSameException()
            } catch (u: UsernameNotFoundException) {
                throw UserNotFoundException()
            }
            return authenticationMng.authenticate(
                    UsernamePasswordAuthenticationToken(
                            creds.username,
                            creds.password,
                            user.authorities
                    )
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun getAuthenticationManager(): AuthenticationManager {
        return authenticationMng
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain,
                                          authResult: Authentication) {
        val userDetails: UserDetails = authResult.principal as UserDetails
        val token: String = JWT.create()
                .withSubject(userDetails.username)
                .withExpiresAt(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token)
        response.writer.append(token)
    }

}