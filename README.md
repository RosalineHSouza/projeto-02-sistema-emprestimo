# Sistema de Empréstimo de Equipamentos

Projeto 2 desenvolvido durante a formação **ElasTech | SoulCode Academy + PagBank — Java + Inteligência Artificial**.

## 📌 Sobre o projeto

O Sistema de Empréstimo de Equipamentos é uma aplicação web desenvolvida para gerenciar o cadastro de equipamentos, colaboradores e operações de empréstimo e devolução.

O sistema aplica regras de negócio para controlar a disponibilidade dos equipamentos, acompanhar os prazos de devolução e identificar empréstimos em atraso.

A aplicação disponibiliza uma API REST desenvolvida com Spring Boot e uma interface web para interação com as funcionalidades.

## 🚀 Tecnologias utilizadas

**Backend**
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven

**Banco de dados**
- H2 — desenvolvimento e testes locais
- PostgreSQL — ambiente de produção

**Frontend**
- HTML
- CSS
- JavaScript
- Fetch API

**Testes e ferramentas**
- JUnit 5
- Postman
- IntelliJ IDEA
- Git e GitHub
- Docker
- Render

## ⚙️ Funcionalidades

- Cadastro e listagem de equipamentos.
- Cadastro e listagem de colaboradores.
- Registro de empréstimos.
- Registro de devoluções.
- Controle da disponibilidade dos equipamentos.
- Cálculo da data prevista de devolução.
- Identificação de empréstimos em atraso.
- Validação das regras de negócio.
- Interface web integrada à API REST.

## 📋 Regras de negócio

| Regra | Descrição |
|---|---|
| R1 | O patrimônio de cada equipamento deve ser único. |
| R2 | Todo equipamento novo deve ser cadastrado como disponível. |
| R3 | O e-mail de cada colaborador deve ser único. |
| R4 | Somente equipamentos disponíveis podem ser emprestados. |
| R5 | Ao registrar um empréstimo, o equipamento fica indisponível e a devolução é prevista para 14 dias após a data do empréstimo. |
| R6 | Um empréstimo já devolvido não pode ser devolvido novamente. |
| R7 | A devolução registra a data atual e torna o equipamento disponível. |
| R8 | Um empréstimo está atrasado quando não foi devolvido e a data prevista já passou. |
| R9 | Um equipamento com histórico de empréstimos não pode ser excluído. |
| R10 | Um colaborador com empréstimo em atraso não pode realizar outro empréstimo. |

## 🏗️ Arquitetura

O backend utiliza uma organização em camadas:

- **Controller:** recebe as requisições HTTP e disponibiliza os endpoints REST.
- **Service:** implementa as operações e valida as regras de negócio.
- **Repository:** realiza o acesso aos dados por meio do Spring Data JPA.
- **Entity:** representa os dados de equipamentos, colaboradores e empréstimos.

As entidades principais são `Equipamento`, `Colaborador` e `Emprestimo`.

A interface web utiliza JavaScript e a Fetch API para consumir os endpoints da aplicação.

## 🔗 Endpoints principais

| Método | Endpoint | Finalidade |
|---|---|---|
| GET | `/equipamentos` | Listar equipamentos |
| POST | `/equipamentos` | Cadastrar equipamento |
| GET | `/colaboradores` | Listar colaboradores |
| POST | `/colaboradores` | Cadastrar colaborador |
| GET | `/emprestimos` | Listar empréstimos |
| POST | `/emprestimos?equipamentoId={id}&colaboradorId={id}` | Registrar empréstimo |
| PUT | `/emprestimos/{id}/devolucao` | Registrar devolução |

## 🧪 Testes automatizados

Foram executados testes com JUnit 5 para validar comportamentos das entidades e operações do sistema.

**Resultado da execução:**
- 11 testes executados.
- 0 falhas.
- 0 erros.
- Build Maven concluído com sucesso.

Também foram realizados testes funcionais da API utilizando o Postman e validações pela interface web.

## ☁️ Publicação

A aplicação foi publicada na plataforma Render, utilizando Docker para implantação do backend e PostgreSQL para persistência dos dados em produção.

**Aplicação online:**

https://projeto-02-sistema-emprestimo.onrender.com

*Observação: o serviço utiliza um plano gratuito e pode apresentar um tempo de inicialização maior após períodos de inatividade. O banco de dados gratuito também possui prazo de expiração.*

## 🎥 Vídeo de apresentação

Link do vídeo demonstrativo: **a incluir após a gravação**.

O vídeo apresenta a arquitetura, as principais funcionalidades, as regras de negócio e uma demonstração do sistema em funcionamento.

## 🎓 Contexto acadêmico

Projeto desenvolvido como atividade prática da formação ElasTech, promovida pela SoulCode Academy em parceria com o PagBank.

O desenvolvimento permitiu aplicar conceitos de programação orientada a objetos, APIs REST, persistência de dados, regras de negócio, testes automatizados e implantação de aplicações Java em nuvem.
