
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
```plaintext
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
```



### Regras de Negócio

_cadastro_  

**RGN1** -  Durante o cadastro, caso a renda mensal do cliente for maior que R$ 2.118,00 o cliente será de conta corrente, senão o cliente será conta do tipo pagamento.

_Uso do banco_  

**RGN2** - O cliente só pode realizar alguma ação após realizar o login com CPF e Senha.

**RGN3** - Todos dos tipos de conta tem 4 saques mensais gratuitos e a Partir do 5º saque é cobrado uma taxa de R$ 6,50 por operação.

**RGN4** - Conta pagamento tem um limite de transferencias de R$ 4.999,99.

**RGN5** - Conta corrente terá cheque especial.
### Rotas da API

| Rotas | Funcionalidade|
|------|--------------|
|```POST``` /cliente/cadastrar|para cadastrar cliente.|
|```POST``` /cliente/login|Realizar o login do cliente.|
|```POST``` /cliente/logout|deslogar o cliente.|
|```PUT``` /cliente/{idCliente}/atualizar|atualiza dados.|
|```PATCH``` /cliente/{idCliente}/mudar-senha|atualizar a senha.|
|```DELETE``` /cliente/{idCliente}/encerrar-conta|deleta o cliente.|
|```POST``` /cliente/{idCliente}/upgrade-conta|upgrade da conta pagamento para conta corrente.|
|```GET``` /conta/{idCliente}|cliente ver a conta.|
|```GET``` /conta/{idConta}/saldo|Verificar saldo atual do cliente|
|```GET``` /conta/{idConta}/extrato|extrato bancario.|
|```PATCH``` /conta/{idConta}/deposito|realizar deposito.|
|```PATCH``` /conta/{idConta}/saque|realizar saque.|
|```PATCH``` /conta/{idConta}/pagamento-de-conta|realizar pagamento.|
|```PATCH``` /conta/{idContaOrigem}/transferencia|realizar transferencia.|
|```PATCH``` /{idConta}/pix|realizar transferencia via pix.|
|```POST``` /conta/{idConta}/pix/cadastro|realizar cadastro de chave pix.|






### Exemplos para uso

- Cadastrar:
```
{
  "cpf": "423.456.789-01",
  "nome": "Fulano de Tal Tal",
  "dataNascimento": "01-01-1991",
  "telefone": "(11) 99499-9999",
  "endereco": "Rua Exemplo, 123",
  "rendaMensal": 3000.00,
  "email": "fulano@example.com",
  "senha": "SenhaSenha123"
}
```

- logar e deslogar:
```
{
    "cpf": "423.456.789-01",
    "senha": "SenhaSenha123"
}
```
