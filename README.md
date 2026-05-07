# FoodFlow 🍔📦

Sistema completo para gestão de pedidos e fluxo de alimentação, desenvolvido com foco em performance e escalabilidade.

## 🚀 Tecnologias

### Backend
- **Java 25** (Utilizando as últimas funcionalidades da linguagem)
- **Spring Boot 3.3.4**
- **Spring Security** com autenticação JWT
- **Flyway** para migrações de banco de dados
- **PostgreSQL**
- **Maven** para gerenciamento de dependências

### Frontend
- **React 18** com TypeScript
- **Vite** para build e desenvolvimento
- **Tailwind CSS** para estilização
- **Axios** para consumo de API

### Infraestrutura
- **Docker & Docker Compose** para orquestração de containers
- **Nginx** para servir o frontend em produção

---

## 🛠️ Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Docker Compose](https://docs.docker.com/compose/install/)

---

## 🏃 Como Rodar a Aplicação

A forma mais simples e recomendada de rodar o projeto completo é via Docker Compose.

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/FoodFlow.git
   cd FoodFlow
   ```

2. **Suba os containers:**
   ```bash
   docker compose up --build
   ```

3. **Acesse as aplicações:**
   - **Frontend:** [http://localhost:5173](http://localhost:5173)
   - **Backend API:** [http://localhost:8080](http://localhost:8080)
   - **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   - **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
   - **Banco de Dados (Postgres):** `localhost:5433`

---

## 📂 Estrutura do Projeto

- `/foodflow`: Código fonte do backend (Spring Boot).
- `/frontend`: Código fonte do frontend (React + Vite).
- `docker-compose.yml`: Configuração da orquestração dos serviços.

---

## 🔐 Autenticação

O sistema utiliza JWT. Para registrar um novo usuário ou fazer login, utilize os endpoints:
- `POST /auth/register`
- `POST /auth/login`

Os tokens devem ser enviados no cabeçalho das requisições protegidas como:
`Authorization: Bearer <seu_token>`

---

## 🏗️ Desenvolvimento Local (Sem Docker)

Se desejar rodar os serviços separadamente para desenvolvimento:

### Backend
1. Navegue até `/foodflow`.
2. Configure o banco de dados no `application.properties`.
3. Execute: `./mvnw spring-boot:run`

### Frontend
1. Navegue até `/frontend`.
2. Instale as dependências: `npm install`
3. Inicie o servidor dev: `npm run dev` (Acessível em [http://localhost:5173](http://localhost:5173))

