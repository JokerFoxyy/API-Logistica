package com.logistica.logistica_api.domain.produto
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "produtos")
data class Produto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var nome: String = "",

    @Column(nullable = false)
    var imageUrl: String = "",

    @Column(nullable = false, length = 1000)
    var descricao: String? = "",

    @Column(nullable = false)
    var preco: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var avaliacao: Double = 0.0,

    @ElementCollection
    @CollectionTable(name = "produto_especificacoes", joinColumns = [JoinColumn(name = "produto_id")])
    @Column(name = "especificacao")
    var especificacoes: Map<String, String> = emptyMap()
)

