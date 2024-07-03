# API de Gerenciamento de Usuários

Esta é uma API RESTful desenvolvida para gerenciar usuários em uma aplicação, incluindo operações como registro, login, mudança de senha e administração de usuários.

## Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- Spring Security
- Swagger (SpringDoc OpenAPI)
- H2 Database (em memória para desenvolvimento / revisão)
- Lombok (para reduzir boilerplate)

## Funcionalidades

A API oferece as seguintes funcionalidades principais:

- Registro de novos usuários (`POST /auth/register`)
- Autenticação de usuários (`POST /auth/login`)
- Alteração de senha (`POST /user/change-password`)
- Administração de usuários (requer perfil de administrador):
    - Listar todos os usuários (`GET /admin/users`)
    - Excluir um usuário pelo ID (`DELETE /admin/users/delete/{id}`)

## Requisitos

- Java 17 ou superior
- Maven 3.x

## Configuração

Para configurar e executar este projeto localmente, siga os passos abaixo:

1. **Clone o repositório**

   ```bash
   git clone https://github.com/davidgamaserrate1/duett-api.git
   cd duett-api
   ```

2. **Execução do Projeto**

    - Execute o projeto usando Maven:

      ```bash
      mvn spring-boot:run
      ```

    - Ou execute o projeto como um aplicativo Java diretamente.

3. **Acesso à Documentação da API**

    - Após iniciar o aplicativo, acesse a documentação da API Swagger em:

      ```
      http://localhost:8080/swagger-ui/index.html
      ```

    - Aqui você pode visualizar todos os endpoints disponíveis, os modelos de dados utilizados e realizar testes interativos com a API.


## Acesso ao Sistema

### Credenciais de Acesso

Ao iniciar o projeto, algumas credenciais são geradas automaticamente conforme configurado no arquivo `DatabaseSeedConfig.java`.

Você pode acessar o sistema utilizando as seguintes credenciais geradas em tempo de execução:

- **Perfil ADMIN:**
    - **Email:** admin@admin
    - **Senha:** admin

- **Perfil USER:**
    - **Email:** alice.johnson@example.com
    - **Senha:** password123

Essas credenciais permitem que você explore os diferentes perfis de usuário e teste as funcionalidades da API de gerenciamento de usuários.

Caso prefira, também é possível cadastrar novos usuários através dos endpoints disponíveis na API.


## Exemplos de Requisições

### Registro de Novo Usuário

```http
POST /auth/register HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "name": "Nome do Usuário",
  "email": "usuario@example.com",
  "password": "senha123",
  "cpf": "123.456.789-00",
  "profile": "USER"
}
```

### Login de Usuário

```http
POST /auth/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "email": "usuario@example.com",
  "password": "senha123"
}
```

### Alteração de Senha

```http
POST /user/change-password HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "id": "id_do_usuario",
  "old_password": "senha_antiga",
  "new_password": "nova_senha"
}
```