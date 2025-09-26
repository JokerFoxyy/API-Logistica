package com.logistica.logistica_api.domain.usuario


import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections

@Table(name = "usuarios")
@Entity(name = "Usuario")
class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val login: String = "",
    val senha: String = ""
) : UserDetails {

    constructor() : this(null, "", "")

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return Collections.singletonList(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
        return senha
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}