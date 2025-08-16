# Challenger Literalura

Este é um projeto Java desenvolvido como parte dos desafios da Alura, com o objetivo de praticar o consumo de APIs, manipulação de dados e persistência com Spring Data JPA. O sistema permite buscar livros a partir da API do Projeto Gutendex e registrar suas informações em um banco de dados local.

---

## Conceitos Abordados

- Java 24
- Spring Boot
- Spring Data JPA
- Postgre SQL
- Consumo de API com `HttpClient`
- Manipulação de dados com `Streams`, `Records`, `Optional`
- Criação de menus interativos no terminal

---

## API Utilizada

- [Gutendex](https://gutendex.com/) – Uma API pública com milhares de livros gratuitos do Project Gutenberg.

---

## Funcionalidades

- Buscar livros por título diretamente da API
- Armazenar livros no banco de dados
- Listar livros registrados
- Filtrar livros por idioma (pt, es, in, fr)
- Listar autores registrados
- Exibir livros agrupados por autor
- Listar autores vivos em determinado periodo
  

---

## Tecnologias e Dependências

- **Java 24**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Postgre SQL**
- **Jackson**

---

## **Estrutura do Projeto**

src

├── main

│   ├── java

│   │   └── br.com.alura.challenger.literalura

│   │       ├── model

│   │       ├── repository

│   │       ├── principal

│   │       └── service

│   └── resources

│       └── application.properties

---

## Como executar o projeto

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/challenger-literalura.git
   cd challenger-literalura

2. Compile o projeto


./mvnw clean install

3. Execute a aplicação
./mvnw spring-boot:run

4. Siga o menu interativo no terminal para navegar pelas opções e testar as funcionalidades.

## **Exemplo de uso**
Ao iniciar o sistema, você verá o seguinte menu:

  1 - Buscar livro pelo titulo
  
  2 - Listar livros registrados
  
  3 - Listar autores registrados
  
  4 - Listar autores vivos em um determinados ano
  
  5 - Listar livros em um determinado idioma

  0 - Sair

📝 Licença
Este projeto foi desenvolvido para fins educacionais como parte do programa Formação Backend ONE com parceria da Alura. Sinta-se à vontade para usar como base e expandir!

🤝 Contribuindo
Contribuições são bem-vindas! Fique à vontade para abrir issues ou pull requests com melhorias, sugestões ou correções.
