package com.sorsix.petadoption.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetails(private val user: User) : UserDetails {
    
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authority = SimpleGrantedAuthority(user.userRole.name)
        val list: MutableList<GrantedAuthority> = ArrayList()
        list.add(authority)
        return list
    }

    override fun isEnabled(): Boolean {
        return user.active
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return user.active
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return user.active
    }

    override fun isAccountNonLocked(): Boolean {
        return user.active
    }
}