# 📋 Board API - Java + Gradle + MySQL

Sistema de gerenciamento de cards para um quadro (Board), desenvolvido em **Java** com **Gradle** e integração ao **MySQL**.  
A aplicação permite **criar, mover, bloquear, desbloquear, excluir e cancelar cards**, possibilitando o controle de tarefas ou demandas em colunas específicas de um board.

---

## 📌 Tecnologias Utilizadas

- Java 17+
- Gradle
- Spring Boot
- MySQL
- Liquibase (para versionamento de banco)
- Lombok
- JPA / Hibernate

---

## ⚙️ Funcionalidades

- ✅ Criar Card
- ✅ Mover Card entre colunas (ex: `To Do` → `Doing` → `Done`)
- ✅ Bloquear Card (impede movimentações e alterações)
- ✅ Desbloquear Card
- ✅ Excluir Card (remoção física)
- ✅ Cancelar Card (status `Cancelado`, mas permanece no histórico)

---

## 📦 Configuração do Banco de Dados

1️⃣ Crie um banco no MySQL:
```sql
CREATE DATABASE board_db;
```
2️⃣ Atualize o application.properties ou application.yml:
```
spring.datasource.url=jdbc:mysql://localhost:3306/board_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=validate
```
3️⃣ Rode o Liquibase para versionar o banco:
```
./gradlew update
```
## 🚀 Como Executar
Clone o projeto:
```
git clone https://github.com/seuusuario/board-api.git
```
Navegue até a pasta:
```
cd board-api
```
Execute a aplicação:
```
./gradlew bootRun
```
## 📬 Endpoints (Exemplo)

| Método | Endpoint               | Descrição                          |
|--------|------------------------|-----------------------------------|
| POST   | `/cards`               | Cria um novo card                 |
| PUT    | `/cards/{id}/move`     | Move card para outra coluna       |
| PUT    | `/cards/{id}/lock`     | Bloqueia um card                  |
| PUT    | `/cards/{id}/unlock`   | Desbloqueia um card               |
| DELETE | `/cards/{id}`          | Exclui permanentemente um card    |
| PUT    | `/cards/{id}/cancel`   | Cancela um card (soft delete)     |

## 📦 Dependências Principais
- Spring Boot Web
- MySQL Connector
- Liquibase (para migrações)

## 🎨 Exemplo de JSON para Criar um Card
```
{
  "title": "Implementar Liquibase",
  "description": "Configurar versionamento do banco de dados.",
  "column": "To Do"
}
```