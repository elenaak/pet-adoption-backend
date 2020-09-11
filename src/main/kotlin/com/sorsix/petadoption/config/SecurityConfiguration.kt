package com.sorsix.petadoption.config

import com.sorsix.petadoption.service.UserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(private val userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/authenticate/signup")
    }

    override fun configure(http: HttpSecurity) {
        http.exceptionHandling().authenticationEntryPoint(UnauthorizedAuthenticationEntryPoint())
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/logout").permitAll()
                .antMatchers(HttpMethod.GET, "/api/pets", "/api/pets/*").permitAll()
                .antMatchers(HttpMethod.POST,"/login/forgot-password","/login/forgot-password/*").permitAll()
                .antMatchers(HttpMethod.GET,"/api/article/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(JWTAuthenticationFilter(authenticationManager(), userDetailsService, passwordEncoder()))
                .addFilter(JWTAuthorizationFilter(userDetailsService, authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().applyPermitDefaultValues()
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addExposedHeader("WWW-Authenticate")
        config.addExposedHeader("Access-Control-Allow-Origin")
        config.addExposedHeader("Access-Control-Allow-Headers")
        config.addAllowedMethod("OPTIONS")
        config.addAllowedMethod("HEAD")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("DELETE")
        config.addAllowedMethod("PATCH")
        source.registerCorsConfiguration("/**", config)
        return source
    }


}