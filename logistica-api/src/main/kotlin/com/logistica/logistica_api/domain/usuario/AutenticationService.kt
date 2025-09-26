package com.logistica.logistica_api.domain.usuario

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AutenticationService(
    private val repository: UsuarioRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return repository.findByLogin(username)
            ?: throw UsernameNotFoundException("Usuário '$username' não encontrado.")
    }
}