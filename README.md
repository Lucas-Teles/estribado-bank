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

**RGN1** - Durante o cadastro, caso a renda mensal do cliente for maior que R$ 2.118,00 o cliente será de conta corrente, senão o cliente será conta do tipo pagamento.

_Uso do banco_

**RGN2** - O cliente só pode realizar alguma ação após realizar o login com CPF e Senha.

**RGN3** - Todos dos tipos de conta tem 4 saques mensais gratuitos e a Partir do 5º saque é cobrado uma taxa de R$ 6,50 por operação.

**RGN4** - Conta pagamento tem um limite de transferencias de R$ 4.999,99.

**RGN5** - Conta corrente terá cheque especial.

### Rotas da API

| Rotas                                        | Funcionalidade                                  |
| -------------------------------------------- | ----------------------------------------------- |
| `POST` /cliente/cadastrar                    | para cadastrar cliente.                         |
| `POST` /cliente/login                        | Realizar o login do cliente.                    |
| `POST` /cliente/logout                       | deslogar o cliente.                             |
| `PUT` /cliente/{idCliente}/atualizar         | atualiza dados.                                 |
| `PATCH` /cliente/{idCliente}/mudar-senha     | atualizar a senha.                              |
| `DELETE` /cliente/{idCliente}/encerrar-conta | deleta o cliente.                               |
| `POST` /cliente/{idCliente}/upgrade-conta    | upgrade da conta pagamento para conta corrente. |
| `GET` /conta/{idCliente}                     | cliente ver a conta.                            |
| `GET` /conta/{idConta}/saldo                 | Verificar saldo atual do cliente                |
| `GET` /conta/{idConta}/extrato               | extrato bancario.                               |
| `PATCH` /conta/{idConta}/deposito            | realizar deposito.                              |
| `PATCH` /conta/{idConta}/saque               | realizar saque.                                 |
| `PATCH` /conta/{idConta}/pagamento-de-conta  | realizar pagamento.                             |
| `PATCH` /conta/{idContaOrigem}/transferencia | realizar transferencia.                         |
| `PATCH` /{idConta}/pix                       | realizar transferencia via pix.                 |
| `POST` /conta/{idConta}/pix/cadastro         | realizar cadastro de chave pix.                 |

### Exemplos para uso

#### cadastro de cliente

**Endpoint:** `POST /cliente/cadastrar`

**Request Body:**

```json
{
  "cpf": "123.456.789-01",
  "nome": "João da Silva",
  "dataNascimento": "01-01-1980",
  "telefone": "123456789",
  "endereco": "Rua Exemplo, 123",
  "rendaMensal": 3000.0,
  "email": "joao@example.com",
  "senha": "senhaSegura123"
}
```

<details>
<sumary>Responses:</sumary>
- 201 Created: Cliente cadastrado com sucesso
- 409 Conflict: Cliente já existe
- 500 Internal Server Error: Erro ao cadastrar o cliente
</details>

#### login de cliente

**Endpoint:** `POST /cliente/login`

**Request Body:**

```json
{
  "cpf": "123.456.789-01",
  "senha": "senhaSegura123"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Cliente logado com sucesso
- 400 Bad Request: Login incorreto
</details>

#### logout de cliente

**Endpoint:** `POST /cliente/logout`

**Request Body:**

```json
{
  "cpf": "123.456.789-01",
  "senha": "senhaSegura123"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Cliente deslogado com sucesso
- 404 Not Found: Cliente não encontrado
- 400 Bad Request: Login ou senha incorreta
</details>

#### Atualização de cliente

**Endpoint:** `PUT /cliente/{idCliente}/atualizar`

**Request Body:**

```json
{
  "cpf": "123.456.789-01",
  "nome": "Novo Nome da Silva",
  "dataNascimento": "01-01-1980",
  "telefone": "987654321",
  "endereco": "Nova Rua, 456",
  "rendaMensal": 4310.0,
  "email": "novoemail@estribadobank.com",
  "senha": "novaSenha123"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Cliente atualizado com sucesso
- 409 Conflict: Cliente não cadastrado ou não está logado
- 500 Internal Server Error: Erro ao atualizar o cliente
</details>

#### Mudança de senha

**Endpoint:** `PATCH /cliente/{idCliente}/mudar-senha`

**Request Body:**

```json
{
  "cpf": "123.456.789-01",
  "email": "joao@example.com",
  "senha": "novaSenha123"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Senha alterada com sucesso
- 404 Not Found: Cliente não encontrado
</details>

#### Remover conta

**Endpoint:** `DELETE /cliente/{idCliente}/encerrar-conta`

<details>
<sumary>Responses:</sumary>
- 200 OK: Conta encerrada com sucesso
- 404 Not Found: Cliente não encontrado
- 500 Internal Server Error: Erro ao removar o cliente
</details>

#### Upgrade de conta

**Endpoint:** `Endpoint: POST /cliente/{idCliente}/upgrade-conta`

<details>
<sumary>Responses:</sumary>
- 200 OK: Conta atualizada com sucesso
- 403 Forbidden: Cliente não cadastrado, não está logado ou não atende à renda mínima
- 500 Internal Server Error: Erro ao removar o cliente
</details>

#### Ver conta

**Endpoint:** `Endpoint: GET /conta/{idCliente}`

<details>
<sumary>Responses:</sumary>
- 200 OK: Conta atualizada com sucesso
- 409 Conflict: Cliente não está logado
- 404 Not Found: Erro ao removar o cliente
</details>

#### Ver saldo

**Endpoint:** `Endpoint: GET /conta/{idConta}/saldo`

<details>
<sumary>Responses:</sumary>
- 200 OK: Retorna o saldo da conta
- 409 Conflict: Cliente não está logado
- 404 Not Found: Conta não encontrada
</details>

#### Ver extrato

**Endpoint:** `Endpoint: GET /conta/{idConta}/extrato?mes={mes}&ano={ano}`

<details>
<sumary>Responses:</sumary>
- 200 OK: Retorna o extrato da conta para o mês e ano especificados
- 409 Conflict: Cliente não está logado
- 404 Not Found: Conta não encontrada
</details>

#### Depósito em conta

**Endpoint:** `Endpoint: PATCH /conta/{idConta}/deposito`

**Request Body:**

```json
{
  "quantia": "150.00"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Depósito realizado com sucesso
- 409 Conflict: Cliente não está logado
- 404 Not Found: Conta não encontrada
</details>

#### Saque em conta

**Endpoint:** `Endpoint: PATCH /conta/{idConta}/saque`

**Request Body:**

```json
{
  "quantia": "70.00"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Saque realizado com sucesso
- 409 Conflict: Cliente não está logado
- 404 Not Found: Conta não encontrada
</details>

#### Pagamento de conta

**Endpoint:** `Endpoint: PATCH /conta/{idConta}/pagamento-de-conta`

**Request Body:**

```json
{
  "quantia": "30.00"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Pagamento realizado com sucesso
- 409 Conflict: Cliente não está logado
- 404 Not Found: Conta não encontrada
</details>

#### Transferência entre conta

**Endpoint:** `Endpoint: PATCH /conta/{idContaOrigem}/transferencia`

**Request Body:**

```json
{
  "idContaDestino": "UUID da conta aqui",
  "quantia": "20.00"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Transferência realizada com sucesso
- 403 Forbidden: Sem limite disponível para transferêcia ou conta não tem permissão
- 409 Conflict: Cliente não está logado
- 404 Not Found: Conta não encontrada
</details>

#### Transferência via pix

**Endpoint:** `Endpoint: PATCH /conta/{idConta}/pix`

**Request Body:**

```json
{
  "chavePix": "chavePixCadastrada",
  "quantia": "100.00"
}
```

<details>
<sumary>Responses:</sumary>
- 200 OK: Transferência via PIX realizada com sucesso
- 403 Forbidden: Sem limite disponível para transferêcia ou chave PIX inválida
- 409 Conflict: Cliente não está logado
- 404 Not Found: Conta não encontrada
</details>

#### Cadastro de chave PIX

**Endpoint:** `Endpoint: PATCH /conta/{idConta}/pix`

**Request Body:**

```json
{
  "chavePix": "chavePixQueVaiCadastrar"
}
```

<details>
<sumary>Responses:</sumary>
- 201 Created: Chave PIX cadastrada com sucesso
- 409 Conflict: Cliente não está logado
- 404 Not Found: Conta não encontrada
</details>

### Mini Documentação de Anotações

<sumary>aqui você vai encontrar uma mini documentação das anotações usadas</sumary>

<details>
<summary>Anotações de Controle</summary>

- **@RestController**: Marca a classe como um controlador onde cada método retorna um objeto de domínio em vez de uma visualização.
- **@RequestMapping**: Mapeia solicitações HTTP para métodos manipuladores específicos.
- **@PostMapping**: Mapeia solicitações HTTP POST para métodos manipuladores específicos.
- **@PutMapping**: Mapeia solicitações HTTP PUT para métodos manipuladores específicos.
- **@PatchMapping**: Mapeia solicitações HTTP PATCH para métodos manipuladores específicos.
- **@GetMapping**: Mapeia solicitações HTTP GET para métodos manipuladores específicos.
- **@DeleteMapping**: Mapeia solicitações HTTP DELETE para métodos manipuladores específicos.

</details>

<details>
<summary>Anotações de Persistência</summary>

- **@Entity**: Especifica que a classe é uma entidade JPA.
- **@Table**: Especifica a tabela principal da entidade.
- **@Id**: Define o identificador primário da entidade.
- **@GeneratedValue**: Especifica a estratégia de geração de valores para o identificador.
- **@UuidGenerator**: Gera identificadores UUID.
- **@Column**: Especifica a coluna mapeada no banco de dados.
- **@UpdateTimestamp**: Anota um campo para armazenar a data e hora da última atualização.
- **@CreationTimestamp**: Anota um campo para armazenar a data e hora da criação da entidade.
- **@OneToOne**: Define um relacionamento de um para um entre entidades.

</details>

<details>
<summary>Anotações de Validação</summary>

- **@NotBlank**: Valida que o campo não é nulo nem vazio após a remoção dos espaços em branco.
- **@NotNull**: Valida que o campo não é nulo.
- **@Pattern**: Valida que o campo corresponde a uma expressão regular especificada.
- **@Size**: Valida que o tamanho do campo está dentro dos limites especificados.
- **@Digits**: Valida que o campo é um número com um número específico de dígitos inteiros e fracionários.
- **@Email**: Valida que o campo é um endereço de e-mail.

</details>

<details>
<summary>Anotações de Serviço e Repositório</summary>

- **@Service**: Indica que a classe é um serviço.
- **@Repository**: Indica que a classe é um repositório e encapsula a lógica de armazenamento, recuperação e busca.

</details>

<details>
<summary>Formatação JSON</summary>

- **@JsonFormat**: Utilizado para formatar os campos ao serializar e desserializar objetos JSON.

</details>

---

Para mais informações sobre cada anotação, consulte a [documentação oficial do Spring](https://spring.io/projects/spring-framework).
