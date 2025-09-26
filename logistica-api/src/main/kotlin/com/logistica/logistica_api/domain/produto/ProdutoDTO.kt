package com.logistica.logistica_api.domain.produto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ProdutoDTO(
    @field:NotNull
    val nome: String,
    val imageUrl: String,
    val descricao: String,
    @field:Positive
    val preco: BigDecimal,
    val avaliacao: Double,
    val especificacoes: List<String>
)
