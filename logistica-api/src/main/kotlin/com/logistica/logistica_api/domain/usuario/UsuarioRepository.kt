package com.logistica.logistica_api.domain.usuario

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails

interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByLogin(login: String?): UserDetails?
}