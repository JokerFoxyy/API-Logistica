package com.logistica.logistica_api.domain.produto

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class ProdutoService(
    private val produtoRepository: ProdutoRepository
) {
    fun listarTodos(pageable: Pageable): Page<Produto> =
        produtoRepository.findAll(pageable)
    fun buscarPorId(id: Long): Produto =
        produtoRepository.findById(id).orElseThrow { EntityNotFoundException("Produto não encontrado") }

    fun criar(dto: ProdutoDTO): Produto {
        val produto = ProdutoBuilder.fromDTO(dto)

        return produtoRepository.save(produto)
    }

    fun atualizar(id: Long, dto: ProdutoDTO): Produto {
        val produto = buscarPorId(id)
        produto.nome = dto.nome
        produto.preco = dto.preco
        produto.imageUrl = dto.imageUrl
        produto.avaliacao=dto.avaliacao
        produto.descricao=dto.descricao
        produto.especificacoes=dto.especificacoes
        return produtoRepository.save(produto)
    }

    fun deletar(id: Long) {
        if (!produtoRepository.existsById(id)) {
            throw RuntimeException("Produto não encontrado")
        }
        produtoRepository.deleteById(id)
    }
}
