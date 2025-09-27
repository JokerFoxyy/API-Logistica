# 📦 API Logística

API RESTful desenvolvida em **Kotlin + Spring Boot 3.5.6** para gerenciamento de produtos.  
O sistema implementa autenticação JWT, CRUD completo, paginação e mapeamento flexível de especificações.

---

## 🧱 Arquitetura

A aplicação segue um padrão em camadas:

- **Controller (`controller/`)**  
  Recebe e responde às requisições HTTP. Ex: `ProdutoController`.

- **Service (`domain/produto/ProdutoService`)**  
  Camada de negócio, centraliza validações e orquestra lógica.

- **Repository (`ProdutoRepository`)**  
  Extensão de `JpaRepository`, responsável pela persistência.

- **DTOs (`ProdutoDTO`)**  
  Evita expor diretamente entidades do banco.

- **Exception Handling**  
  Um `@RestControllerAdvice` centraliza erros como `404 Not Found`, `400 Bad Request` e exceções genéricas.

### Design Patterns aplicados
- **Dependency Injection** → desacoplamento via Spring.  
- **DTO Pattern** → isolamento da camada de domínio.  
- **Exception Handler centralizado** → tratamento uniforme de erros.  
- **ElementCollection** → modelagem flexível de `especificacoes` (mapa chave-valor).  

---

## 🚀 Como rodar

### Requisitos
- JDK 17+
- Maven
- (Opcional) MySQL/Postgres configurado. Para testes locais, usamos **H2**.

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/JokerFoxyy/API-Logistica.git
   cd API-Logistica
   ```

2. Configure o `application.yml`. Exemplo usando H2:
   ```yaml
   spring:
     datasource:
       url: jdbc:h2:mem:logistica
       driverClassName: org.h2.Driver
       username: sa
       password:
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
     h2:
       console:
         enabled: true
   ```

3. Compile e rode:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. API disponível em:  
   [http://localhost:8080](http://localhost:8080)

5. Documentação técnica OPEN API disponível em:
  [http://localhost:8080/swagger-ui/index.html]

---

## 🔑 Autenticação (Login)

A API usa JWT.  
Primeiro, faça login:

**POST /auth/login**
```json
{
  "username": "usuario",
  "password": "senha"
}
```

Resposta:
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Use o token em todas as requisições:
```
Authorization: Bearer <access_token>
```

---

## 📘 Endpoints de Produtos

### Listar com paginação
`GET /produtos?page=0&size=5&sort=preco,desc`

Exemplo de resposta:
```json
{
  "content": [
    {
      "id": 1,
      "nome": "Notebook Dell Inspiron",
      "imageUrl": "https://example.com/dell.jpg",
      "descricao": "Notebook Intel i7, 16GB RAM, SSD 512GB",
      "preco": 5500.0,
      "rating": 4.5,
      "especificacoes": {
        "Processador": "Intel Core i7",
        "RAM": "16GB",
        "Armazenamento": "SSD 512GB"
      }
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### Buscar por ID
`GET /produtos/{id}`

### Criar produto
`POST /produtos`
```json
{
  "nome": "Notebook Dell Inspiron",
  "imageUrl": "https://example.com/dell.jpg",
  "descricao": "Notebook Intel i7, 16GB RAM, SSD 512GB",
  "preco": 5500.0,
  "avaliacao": 4.5,
  "especificacoes": {
    "Processador": "Intel Core i7",
    "RAM": "16GB",
    "Armazenamento": "SSD 512GB"
  }
}

```

### Atualizar produto
`PUT /produtos/{id}`  
Mesmo payload do `POST`.

### Deletar produto
`DELETE /produtos/{id}`  
Retorna `204 No Content`.

---

## 🛠 Tratamento de erros

Exemplo de erro ao buscar produto inexistente:

```json
{
  "timestamp": "2025-09-26T12:34:56.789",
  "status": 404,
  "error": "Not Found",
  "message": "Produto com id 123 não encontrado"
}
```

---

## ✅ Próximos passos sugeridos
- Adicionar validação com `@Valid` e anotações como `@NotBlank`.  
- Documentação Swagger/OpenAPI para facilitar consumo.  
- Testes unitários com **JUnit 5 + MockK**.  
- Versionamento da API (`/v1/produtos`).  
- Integração com banco real (MySQL/Postgres).  

---

Feito com ❤️ em Kotlin.
