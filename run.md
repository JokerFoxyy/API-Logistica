# 🚀 Guia de Execução da API de Logística

Este documento descreve como rodar a aplicação localmente e como utilizar a **Collection do Postman** para testar os endpoints.  

---

## ✅ Pré-requisitos

- **Java 17** ou superior  
- **Maven** instalado e configurado  
- **Docker** (opcional, caso queira rodar banco externo, mas a aplicação já usa **H2** em memória)  
- **Postman** ou **Insomnia** para testar os endpoints  

---

## ▶️ Como rodar localmente

1. Clone o repositório:
   ```bash
   descompacte o arquivo .zip
   cd API-Logistica
   ```

2. Compile e rode a aplicação ou rode através de alguma IDE (IntelliJ):
   ```bash
   mvn spring-boot:run
   ```

3. A aplicação estará disponível em:
   ```
   http://localhost:8080
   ```

---

## 🔑 Autenticação (Login)

Antes de acessar os endpoints protegidos, é necessário realizar o **login** para obter o **token JWT**.

### Request de Login
- **URL:** `POST http://localhost:8080/auth/login`  
- **Body (JSON):**
   ```json
   {
     "username": "teste",
     "password": "123456"
   }
   ```

### Response esperado:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

> O valor do campo **token** deve ser usado no **Authorization Header** das próximas requisições:  
```
Authorization: Bearer <token>
```

---

## 📬 Collection do Postman

Uma **collection do Postman** será disponibilizada no repositório para facilitar os testes.

### Importando
1. Abra o **Postman**  
2. Vá em **Import** → selecione o arquivo `API-Logistica.postman_collection.json`  
3. Configure a variável de ambiente `{{base_url}}` como:
   ```
   http://localhost:8080
   ```
4. Configure a variável `{{auth_token}}` com o token obtido na request de **Login**.  

---

## 📌 Exemplos de Requests

### Criar Produto
```http
POST /produtos
Authorization: Bearer {{auth_token}}
Content-Type: application/json
```

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

### Listar Produtos
```http
GET /produtos?page=0&size=10
Authorization: Bearer {{auth_token}}
```

### Atualizar Produto
```http
PUT /produtos/1
Authorization: Bearer {{auth_token}}
Content-Type: application/json
```

```json
{
  "nome": "Notebook Dell Inspiron Plus",
  "imageUrl": "https://example.com/dellplus.jpg",
  "descricao": "Notebook atualizado com 32GB RAM",
  "preco": 6500.0,
  "avaliacao": 4.8,
  "especificacoes": {
    "Processador": "Intel Core i9",
    "RAM": "32GB",
    "Armazenamento": "SSD 1TB"
  }
}
```

### Deletar Produto
```http
DELETE /produtos/1
Authorization: Bearer {{auth_token}}
```

---

## 📊 Observações

- O banco de dados é **H2 em memória**:  
  - Console disponível em:  
    ```
    http://localhost:8080/h2-console
    ```
  - JDBC URL: `jdbc:h2:mem:logistica`  
  - User: `sa`  
  - Password: (em branco)

- A cada restart da aplicação, os dados são resetados.  
