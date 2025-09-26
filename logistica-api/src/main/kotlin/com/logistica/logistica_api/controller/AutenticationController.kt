package com.logistica.logistica_api.controller

import com.logistica.logistica_api.domain.usuario.DadosAutenticacao
import com.logistica.logistica_api.infra.security.DadosTokenJWT
import com.logistica.logistica_api.infra.security.TokenService
import com.logistica.logistica_api.domain.usuario.Usuario

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class AutenticacaoController(
    private val manager: AuthenticationManager,
    private val tokenService: TokenService
) {
    @PostMapping
    fun efetuarLogin(@RequestBody @Valid dados: DadosAutenticacao): ResponseEntity<DadosTokenJWT> {

        val autToken = UsernamePasswordAuthenticationToken(dados.login, dados.senha)

        val authentication = manager.authenticate(autToken)

        val usuario = authentication.principal as Usuario
        val tokenJWT = tokenService.gerarToken(usuario)

        return ResponseEntity.ok(DadosTokenJWT(tokenJWT))
    }
}