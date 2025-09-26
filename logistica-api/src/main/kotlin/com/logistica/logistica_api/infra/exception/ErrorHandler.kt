package com.logistica.logistica_api.infra.exception

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime


@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Not Found",
            message = ex.message
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleError400(ex: MethodArgumentNotValidException): ResponseEntity<*> {
        val erros = ex.fieldErrors

        return ResponseEntity.badRequest().body(erros.map(::DadosErroValidacao))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun tratarErro400(ex: HttpMessageNotReadableException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(ex.message)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleErrorBadCredentials(): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas")
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleErrorAccessDenied(): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado")
    }

//    @ExceptionHandler(ValidacaoException::class)
//    fun handleErrorValidacao(ex: ValidacaoException): ResponseEntity<*> {
//        return ResponseEntity.badRequest().body(ex.message)
//    }

    @ExceptionHandler(Exception::class)
    fun handleError500(ex: Exception): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.localizedMessage)
    }

    data class DadosErroValidacao(
        val campo: String,
        val mensagem: String
    ) {
        constructor(erro: FieldError) : this(erro.field, erro.defaultMessage ?: "Valor inválido")
    }

    data class ErrorResponse(
        val timestamp: LocalDateTime = LocalDateTime.now(),
        val status: Int,
        val error: String,
        val message: String?
    )
}