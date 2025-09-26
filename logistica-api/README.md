# 📦 Logistica API

API RESTful para gerenciamento e comparação de itens de logística.

## 🚀 Tecnologias
- Kotlin
- Spring Boot 3
- Maven
- MockK / JUnit 5 para testes

## 📂 Estrutura
- `domain/` → Contém a lógica de domínio do negócio, dividido por pastas que representam os domínios
- `controller/` → Endpoints REST
- `infra/` → Contém a lógica de infraestrutura como configurações de segurança e tratamento de exceções
- - `exception/` → Tratamento de erros customizados

## 📡 Endpoints
### `GET /api/products`
Retorna lista de produtos com os campos:
- `name`
- `imageUrl`
- `description`
- `price`
- `rating`
- `specifications`

### Exemplo de resposta
```json
[
  {
    "name": "Notebook Dell Inspiron",
    "imageUrl": "https://example.com/dell.jpg",
    "description": "Notebook Intel i7, 16GB RAM, SSD 512GB",
    "price": 5500.0,
    "rating": 4.5,
    "specifications": {
      "Processador": "Intel Core i7",
      "RAM": "16GB",
      "Armazenamento": "SSD 512GB"
    }
  }
]
