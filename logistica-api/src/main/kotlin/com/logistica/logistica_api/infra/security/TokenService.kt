package com.logistica.logistica_api.infra.security

import com.logistica.logistica_api.domain.usuario.Usuario


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {

    @Value("\${api.security.token.secret}")
    private lateinit var secret: String

    fun gerarToken(usuario: Usuario): String {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            JWT.create()
                .withIssuer("API Logistica")
                .withSubject(usuario.login)
                .withExpiresAt(dataExpiracao())
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw RuntimeException("erro ao gerar token jwt", exception)
        }
    }

    fun getSubject(tokenJWT: String): String {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            JWT.require(algorithm)
                .withIssuer("API Logistica")
                .build()
                .verify(tokenJWT)
                .subject
        } catch (exception: JWTVerificationException) {
            throw RuntimeException("Token JWT inválido ou expirado!")
        }
    }

    private fun dataExpiracao(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
    }
}