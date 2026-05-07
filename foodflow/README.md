# FoodFlow

FoodFlow é um sistema de gerenciamento de pedidos para restaurantes, focado em proporcionar uma experiência fluida para atendentes e cozinha. O projeto foi desenvolvido com TDD, seguindo princípios de **Clean Architecture** e **Domain-Driven Design (DDD)**, garantindo um código altamente testável e fácil de manter.

## 🚀 Tecnologias

- **Java 25**
- **Spring Boot 4.0.5**
- **Spring Security & JWT** (Autenticação e Autorização)
- **Spring Data JPA** (Persistência)
- **PostgreSQL** (Banco de dados)
- **Flyway** (Migrações de banco de dados)
- **Lombok** (Produtividade)
- **JUnit 5 & Pitest** (Suíte de testes e cobertura de mutação)
- **Springdoc OpenAPI / Swagger UI** (Documentação interativa da API)
- **Maven** (Gerenciamento de dependências)

## 🏗️ Estrutura do Projeto

O projeto está organizado em camadas para separar preocupações:

- **`application`**: Contém os Casos de Uso (Use Cases) que orquestram a lógica de negócio.
- **`domain`**: O coração do sistema, contendo Entidades, Interfaces de Repositório e Exceções de Domínio.
- **`infra`**: Implementações técnicas, como persistência JPA, segurança e mapeadores.
- **`web`**: Camada de interface REST, contendo Controllers e DTOs de entrada/saída.

## ✨ Principais Funcionalidades

### Gestão de Pedidos
- **Abertura de Mesa**: Inicia um novo pedido vinculado a uma mesa específica.
- **Adição de Itens**: Permite adicionar itens do menu ao pedido, com suporte a observações e adicionais (add-ons).
- **Fluxo de Status**: Acompanhamento do ciclo de vida do item (Pendente -> Em Preparo -> Pronto -> Entregue).
- **Fechamento de Conta**: Cálculo automatizado do total, aplicação de descontos e divisão por pessoa.

### Segurança
- **Autenticação JWT**: Acesso protegido a endpoints sensíveis.
- **Controle de Acesso**: Diferenciação entre papéis de usuários (ex: Garçom, Admin).

### Inventário e Menu
- **Controle de Disponibilidade**: Verificação automática de estoque ao adicionar itens.
- **Gestão de Adicionais**: Personalização de pedidos com itens extras.

## 🛠️ Instalação e Execução

### Pré-requisitos
- Java 25
- PostgreSQL
- Maven 3.9+

### Configuração
1. Configure as variáveis de ambiente no seu sistema ou no `application.properties`:
   - `POSTGRES_HOST`, `POSTGRES_PORT`, `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD`.
   - `JWT_SECRET`: Uma chave secreta de pelo menos 32 caracteres.

2. Execute as migrações e inicie o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📖 Documentação da API (Swagger)

Com a aplicação rodando, acesse a documentação interativa:

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

A documentação cobre todos os endpoints agrupados por domínio (**Autenticação**, **Pedidos**, **Mesas**, **Cardápio**), com os status codes de resposta, corpo das requisições e suporte a autenticação JWT via botão **Authorize**.

## 🧪 Testes

O projeto prioriza a qualidade através de testes rigorosos:

- **Unitários**: Testes das regras de negócio nos Casos de Uso.
- **Integração**: Verificação da camada de persistência e controllers.
- **Mutação (Pitest)**: Avaliação da eficácia dos testes unitários.

Para rodar a suíte completa:
```bash
./mvnw test
```

Para gerar o relatório de mutação:
```bash
./mvnw pitest:mutationCoverage
```

---
Desenvolvido como parte do ecossistema FoodFlow.
