# Sistema de Pizzaria

Trabalho 1 da disciplina de EProjeto e Arquitetura de Software

## 🚀 Tecnologias Utilizadas
- **Java 21+**
- **Spring Boot 3.5.4**
- **Spring Data JPA (Hibernate)**
- **Banco de Dados H2 (Em memória)**

## 🏗️ Estrutura do Projeto
O projeto está organizado seguindo a separação de camadas do DDD:
* `Dominio`: Entidades de negócio e interfaces de repositórios.
* `Aplicacao`: Casos de uso (Use Cases) do sistema.
* `Adaptadores`: Implementações de persistência (JPA), controladores REST e configurações.

## ⚙️ Como Executar
1. Certifique-se de ter o Maven instalado.
2. Clone o repositório.
3. No terminal, na raiz do projeto, execute:
   ```bash
   ./mvnw spring-boot:run
