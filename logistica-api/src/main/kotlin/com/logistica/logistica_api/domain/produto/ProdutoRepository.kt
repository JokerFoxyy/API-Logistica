package com.logistica.logistica_api.domain.produto

import org.springframework.data.jpa.repository.JpaRepository

interface ProdutoRepository : JpaRepository<Produto, Long>

