package com.sorsix.petadoption.config

import com.sorsix.petadoption.domain.exception.PasswordsNotTheSameException
import com.sorsix.petadoption.domain.exception.UserNotFoundException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class UnauthorizedAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

    @Throws(IOException::class, ServletException::class, PasswordsNotTheSameException::class, UserNotFoundException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        response.sendError(400, "Invalid username or password")
    }
}