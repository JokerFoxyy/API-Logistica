package com.logistica.logistica_api.domain.produto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ProdutoDTO(
    @field:NotBlank(message = "O nome do produto é obrigatório")
    val nome: String,

    @field:NotNull(message = "O preço é obrigatório")
    @field:Positive(message = "O preço deve ser maior que zero")
    val preco: BigDecimal,

    @field:NotBlank(message = "A URL da imagem é obrigatória")
    val imageUrl: String,

    val descricao: String? = null,

    @field:NotNull(message = "A avaliação é obrigatória")
    val avaliacao: Double,

    val especificacoes: Map<String, String> = emptyMap()
)
