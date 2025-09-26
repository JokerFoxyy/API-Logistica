package com.logistica.logistica_api.controller
import com.logistica.logistica_api.domain.produto.ProdutoService
import com.logistica.logistica_api.domain.produto.Produto
import com.logistica.logistica_api.domain.produto.ProdutoDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

// Modelo de produto
data class Product(
    val name: String,
    val imageUrl: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val specifications: Map<String, String>
)

@RestController
@RequestMapping("/produtos")
class ProdutoController(
    private val produtoService: ProdutoService
) {

    @GetMapping
    fun listarProdutos(@PageableDefault(size =5)  paginacao:Pageable) : ResponseEntity<Page<Produto>> =
        ResponseEntity.ok(produtoService.listarTodos(paginacao))

    @GetMapping("/{id}")
    fun buscarProduto(@PathVariable id: Long): ResponseEntity<Produto> =
        ResponseEntity.ok(produtoService.buscarPorId(id))

    @PostMapping
    fun criarProduto(@RequestBody dto: ProdutoDTO): ResponseEntity<Produto> {
        val novoProduto = produtoService.criar(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto)
    }

    @PutMapping("/{id}")
    fun atualizarProduto(@PathVariable id: Long, @RequestBody dto: ProdutoDTO): ResponseEntity<Produto> {
        val atualizado = produtoService.atualizar(id, dto)
        return ResponseEntity.ok(atualizado)
    }

    @DeleteMapping("/{id}")
    fun deletarProduto(@PathVariable id: Long): ResponseEntity<Void> {
        produtoService.deletar(id)
        return ResponseEntity.noContent().build()
    }
}

