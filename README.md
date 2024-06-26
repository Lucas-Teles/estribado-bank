
# Estribado Bank

Estribado Bank é um banco inovador e de futuro promissor ue vai trazer segurança e educação financeira para todes.


### Descrição do projeto    

Projeto para conclusão do curso de desenvolvimento back-end da afrócodigos, objetivo desenvolver uma API Rest de um sistema interno de banco, aqui teremos o sistema do meu banco EstribadoBank. Aproveite !!
### Ferramentas utilizada

- **IDE - IntelliJ**
- **Java 17**
- **Maven**
- **PostgreeSQL 16**
- **Insominia**
- **AWS**


### Usando a API localmente

Para usar localmente é necessário ter instalado:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Insomnia](https://insomnia.rest/download)
- [Git](https://git-scm.com/downloads)
1. Clonar o repositorio pelo git:
```bash
git clone https://github.com/Lucas-Teles/estribado-bank.git
```
2. Criar uma database de nome no pgAdmin (PostgreSQL) -> **estribadobank**
3. Abrir o projeto no intelliJ
  - ir na pasta resources/application.properties e em spring.datasource.password=(colocar sua senha).
4. Iniciar o BancoAplication.java no intelliJ
5. executar o insomnia e ultilize as [rotas]() para testar

### Usando a API online

Para acessar a API pelo servidor basta clicar aqui -> [EstribadoBank]()
- Caso não esteja conectando no momento de acesso. Peço que abra uma issue.


### Estrutura em camadas do projeto

src
└── main
    ├── java
    │   └── br
    │       └── com
    │           └── estribadobank
    │               └── banco
    │                   ├── config
    │                   │   └── DataBaseInitializer.java
    │                   ├── controller
    │                   │   ├── ClienteController.java
    │                   │   ├── ContaController.java
    │                   │   └── TransacaoController.java
    │                   ├── exception
    │                   │   ├── ClienteException.java
    │                   │   └── ContaException.java
    │                   ├── model
    │                   │   └── entity
    │                   │       ├── Cliente.java
    │                   │       ├── Conta.java
    │                   │       ├── ContaCorrente.java
    │                   │       ├── ContaPagamento.java
    │                   │       └── Transacao.java
    │                   ├── repository
    │                   │   ├── ClienteRepository.java
    │                   │   ├── ContaRepository.java
    │                   │   └── TransacaoRepository.java
    │                   ├── service
    │                   │   ├── ClienteService.java
    │                   │   └── ContaService.java
    │                   └── BancoApplication.java
    └── resources
        └── application.properties


