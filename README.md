# Categories Service — Microserviço de Gestão Inteligente de Categorias e Objetivos

> **Organize metas por categoria, integre ecossistemas distribuídos e entregue APIs seguras com a mesma robustez que o mercado exige de produtos em escala.**

---

## Slogan / Chamada curta

**A camada de categorias que une seu domínio de negócio à arquitetura de microsserviços — rápida, documentada e pronta para produção.**

---
<img width="1536" height="1024" alt="Generatedimage_1774910042281" src="https://github.com/user-attachments/assets/48f801fd-c0ca-4c2f-b3ad-049f26ef5e17" />



## Introdução e visão geral

### O problema

Produtos de produtividade, educação e *goal tracking* precisam agrupar objetivos de forma consistente, por usuário, sem acoplar regras de negócio em um monólito gigante. Ao mesmo tempo, segurança (validação de identidade), persistência confiável e comunicação com outros serviços (objetivos, autenticação, notificações) tornam-se requisitos obrigatórios — não opcionais.

### A solução

O **Categories Service** é um microsserviço **Spring Boot** responsável pelo ciclo de vida das **categorias de objetivos**: criação, atualização, listagem, remoção e associação com objetivos hospedados em serviços externos. Ele expõe uma API REST clara, valida acesso via integração com autenticação, persiste dados em **MySQL** e orquestra chamadas declarativas com **OpenFeign**.

### Benefícios para o negócio e para o usuário final

| Benefício | Impacto |
|-----------|---------|
| **Separação de responsabilidades** | Evolução independente do módulo de categorias sem travar o restante da plataforma. |
| **Segurança na camada de API** | Operações condicionadas à validação de token, reduzindo superfície de risco. |
| **Integração nativa com ecossistema** | Comunicação padronizada com serviços de objetivos e configuração centralizada. |
| **Observabilidade e operação** | *Actuator*, logs estruturados e empacotamento **Docker** facilitam deploy e monitoramento. |
| **Documentação viva da API** | **OpenAPI (Swagger)** acelera onboarding de times e parceiros. |

---

## Funcionalidades principais

1. **Criar categoria para o usuário**  
   *Propósito:* permitir que cada usuário estruture suas metas em grupos lógicos (ex.: “Carreira”, “Saúde”).  
   *Valor:* dados modelados de forma explícita, prontos para relatórios e UX personalizada.

2. **Listar todas as categorias do usuário**  
   *Propósito:* fornecer visão consolidada do portfólio de categorias.  
   *Valor:* base para dashboards, menus e fluxos de navegação no *front-end*.

3. **Atualizar categoria (nome e descrição)**  
   *Propósito:* manter informações alinhadas à evolução do usuário.  
   *Valor:* flexibilidade sem recriar entidades ou perder vínculos históricos.

4. **Remover categoria**  
   *Propósito:* gestão do ciclo de vida e limpeza de dados obsoletos.  
   *Valor:* governança de dados e conformidade com políticas de retenção.

5. **Associar objetivo a uma categoria**  
   *Propósito:* vincular metas já existentes no serviço de objetivos à categoria correta.  
   *Valor:* modelo rico para priorização, filtros e gamificação.

6. **Desassociar objetivo de uma categoria**  
   *Propósito:* corrigir classificações sem excluir o objetivo no serviço de origem.  
   *Valor:* correções rápidas com menor impacto operacional.

7. **Listar objetivos por categoria**  
   *Propósito:* agregar dados da categoria com detalhes obtidos via **Feign** do microsserviço de objetivos.  
   *Valor:* endpoint de leitura orientado ao produto, com consistência entre persistência local e serviços remotos.

8. **Processamento assíncrono nos endpoints**  
   *Propósito:* uso de `@Async` com *executor* dedicado para melhor utilização de recursos sob carga.  
   *Valor:* demonstra preocupação com desempenho e padrões modernos de API reativa-assíncrona em Spring.

9. **Tratamento centralizado de exceções**  
   *Propósito:* respostas HTTP previsíveis para erros de banco, recurso não encontrado ou falhas em serviços externos.  
   *Valor:* melhor experiência para consumidores da API e depuração mais rápida.

10. **Integração com configuração centralizada (Spring Cloud Config)**  
    *Propósito:* propriedades sensíveis e URLs de serviços gerenciadas fora do *jar*.  
    *Valor:* padrão de **12-factor** e deploy repetível em múltiplos ambientes.

11. **Documentação OpenAPI**  
    *Propósito:* descrever contratos, exemplos e operações diretamente na aplicação.  
    *Valor:* reduz atrito comercial e técnico na integração B2B ou entre squads.

---

## Tecnologias utilizadas

| Categoria | Tecnologia |
|-----------|------------|
| Linguagem | **Java 21** |
| Framework | **Spring Boot 3.4.x** |
| Web & REST | **Spring Web**, **Springdoc OpenAPI** (Swagger UI) |
| Persistência | **Spring Data JPA**, **MySQL** (`mysql-connector-j`) |
| Microsserviços | **Spring Cloud OpenFeign**, **Spring Cloud Config** |
| Segurança / JWT | **Auth0 java-jwt**, validação com chave pública (`public.pem`) |
| Utilitários | **Lombok** |
| Observabilidade | **Spring Boot Actuator** |
| Logging | **Logback** (`logback-spring.xml`) |
| Testes | **Spring Boot Test**, **REST Assured**, **JavaFaker** |
| Containerização | **Docker** (imagem **Eclipse Temurin 21** Alpine) |
| Build | **Apache Maven** |
| CI/CD | **GitHub Actions** (build, empacotamento, deploy via SSH em VPS) |

**Por que essas escolhas importam no portfólio**

- **Java 21 + Spring Boot 3:** stack alinhada ao que grandes empresas adotam hoje (LTS, performance, ecossistema maduro).  
- **JPA + MySQL:** modelo relacional claro para dados de domínio com transações ACID.  
- **OpenFeign:** contratos HTTP tipados e testáveis entre serviços — padrão em arquiteturas distribuídas.  
- **Spring Cloud Config:** separação entre código e configuração, essencial em pipelines profissionais.  
- **OpenAPI:** documentação que vende o projeto para quem avalia qualidade de entrega em minutos.

---

## Demonstração visual
![Documentação Swagger — Categories API](docs/assets/swagger-categories.png)

## Contribuição

Contribuições são bem-vindas para elevar ainda mais o valor do portfólio coletivo.

1. Faça um *fork* do repositório.  
2. Crie uma branch descritiva: `git checkout -b feature/minha-melhoria`.  
3. Commit com mensagens claras, alinhadas ao padrão do projeto.  
4. Abra um *Pull Request* explicando **o quê**, **por quê** e como validar.  
5. Garanta que o build Maven e os testes relevantes passem antes da revisão.

Para mudanças em integrações externas (Config Server, URLs, segredos), documente no PR os pré-requisitos para reproduzir o ambiente.

---

## Licença

**Não há arquivo `LICENSE` na raiz deste repositório no momento.** Recomenda-se adicionar um explicitamente (por exemplo **MIT**, **Apache 2.0** ou licença proprietária da organização) para evitar ambiguidade jurídica. Até lá, o código deve ser tratado como **todos os direitos reservados** pelo detentor do repositório, salvo acordo em contrário.

---

## Agradecimentos

- À equipe e à comunidade **Spring** pela plataforma que sustenta microsserviços em produção no mundo inteiro.  
- Aos mantenedores de **Springdoc OpenAPI**, **OpenFeign** e do ecossistema **Docker**, que tornam entregas profissionais mais previsíveis.  
- A quem revisar este repositório no GitHub — seu tempo de avaliação é o combustível que transforma código em oportunidade.

---

## Contato

| Canal | Link / Informação |
|--------|-------------------|
| **LinkedIn** | [https://www.linkedin.com/in/victor-teixeira-354a131a3/](https://www.linkedin.com/in/victor-teixeira-354a131a3/) |
| **GitHub** | [https://github.com/victorteixeirasilva](https://github.com/victorteixeirasilva) |
| **E-mail** | victor.teixeira@inovasoft.tech |

---

**Categories Service** — *microsserviço de categorias para ecossistemas que evoluem com método, segurança e clareza de propósito.*
