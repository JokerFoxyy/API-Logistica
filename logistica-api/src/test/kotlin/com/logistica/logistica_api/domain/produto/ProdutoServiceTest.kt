package com.logistica.logistica_api.domain.produto

import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class ProdutoServiceTest {

    private lateinit var produtoRepository: ProdutoRepository
    private lateinit var produtoService: ProdutoService

    @BeforeEach
    fun setup() {
        produtoRepository = mock()
        produtoService = ProdutoService(produtoRepository)
    }

    @Test
    fun `deve listar todos os produtos`() {
        val pageable: Pageable = mock()
        val produtos = listOf(Produto(id = 1L, nome = "Notebook", preco = 5000.0.toBigDecimal()))
        whenever(produtoRepository.findAll(pageable)).thenReturn(PageImpl(produtos))

        val resultado: Page<Produto> = produtoService.listarTodos(pageable)

        assertEquals(1, resultado.totalElements)
        assertEquals("Notebook", resultado.content[0].nome)
        verify(produtoRepository).findAll(pageable)
    }

    @Test
    fun `deve buscar produto por id existente`() {
        val produto = Produto(id = 1L, nome = "Celular", preco = 2000.0.toBigDecimal())
        whenever(produtoRepository.findById(1L)).thenReturn(Optional.of(produto))

        val resultado = produtoService.buscarPorId(1L)

        assertEquals("Celular", resultado.nome)
        verify(produtoRepository).findById(1L)
    }

    @Test
    fun `deve lançar exceção ao buscar produto inexistente`() {
        whenever(produtoRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows(EntityNotFoundException::class.java) {
            produtoService.buscarPorId(1L)
        }
    }

    @Test
    fun `deve criar produto corretamente`() {
        val preco = 1500.0.toBigDecimal()

        val dto = ProdutoDTO(
            nome = "Tablet",
            preco = preco,
            imageUrl = "url",
            avaliacao = 4.0,
            descricao = "Tablet Android",
            especificacoes = mapOf("Tela" to "10 polegadas")
        )
        val produto = ProdutoBuilder.fromDTO(dto)

        doAnswer { it.arguments[0] as Produto }.whenever(produtoRepository).save(any())

        val resultado = produtoService.criar(dto)

        assertEquals("Tablet", resultado.nome)
        assertEquals(preco, resultado.preco)
        verify(produtoRepository).save(any())
    }

    @Test
    fun `deve atualizar produto existente`() {
        val precoInicial = 3000.0.toBigDecimal()
        val precoNovo = 7000.0.toBigDecimal()

        val produtoExistente = Produto(
            id = 1L,
            nome = "PC",
            preco = precoInicial,
            imageUrl = "oldUrl",
            avaliacao = 3.0,
            descricao = "Antigo",
            especificacoes = mapOf("RAM" to "4GB")
        )
        val dto = ProdutoDTO(
            nome = "PC Gamer",
            preco = precoNovo,
            imageUrl = "newUrl",
            avaliacao = 5.0,
            descricao = "Potente",
            especificacoes = mapOf("RAM" to "16GB")
        )

        whenever(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente))

        doAnswer { it.arguments[0] as Produto }.whenever(produtoRepository).save(any())

        val resultado = produtoService.atualizar(1L, dto)

        assertEquals("PC Gamer", resultado.nome)
        assertEquals(precoNovo, resultado.preco)
        assertEquals("newUrl", resultado.imageUrl)
        assertEquals(5.0, resultado.avaliacao)
        assertEquals("Potente", resultado.descricao)
        assertEquals(mapOf("RAM" to "16GB"), resultado.especificacoes)
        verify(produtoRepository).save(produtoExistente)
    }

    @Test
    fun `deve lançar exceção ao tentar deletar produto inexistente`() {
        whenever(produtoRepository.existsById(1L)).thenReturn(false)

        assertThrows(RuntimeException::class.java) {
            produtoService.deletar(1L)
        }
        verify(produtoRepository, never()).deleteById(1L)
    }

    @Test
    fun `deve deletar produto existente`() {
        whenever(produtoRepository.existsById(1L)).thenReturn(true)

        produtoService.deletar(1L)

        verify(produtoRepository).deleteById(1L)
    }
}
