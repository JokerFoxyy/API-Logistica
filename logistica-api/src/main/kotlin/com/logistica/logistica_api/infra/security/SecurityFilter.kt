package com.logistica.logistica_api.infra.security


import com.logistica.logistica_api.domain.usuario.UsuarioRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class SecurityFilter(
    private val tokenService: TokenService,
    private val repository: UsuarioRepository
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenJWT = recuperarToken(request)

        if (tokenJWT != null) {
            val subject = tokenService.getSubject(tokenJWT)

            val usuario = repository.findByLogin(subject)

            val authentication = UsernamePasswordAuthenticationToken(usuario, null, usuario?.authorities)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")?.let { header ->
            if (header.startsWith("Bearer ")) {
                header.replace("Bearer ", "")
            } else {
                null
            }
        }
    }
}