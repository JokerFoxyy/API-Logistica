package com.logistica.logistica_api.domain.produto

 object ProdutoBuilder {
    fun fromDTO(dto: ProdutoDTO): Produto =
        Produto(
            nome = dto.nome,
            preco = dto.preco,
            imageUrl = dto.imageUrl,
            descricao = dto.descricao,
            avaliacao = dto.avaliacao,
            especificacoes = dto.especificacoes
        )


}