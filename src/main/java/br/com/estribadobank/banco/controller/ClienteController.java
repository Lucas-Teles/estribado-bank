package br.com.estribadobank.banco.controller;

import br.com.estribadobank.banco.Exception.ClienteException;
import br.com.estribadobank.banco.model.entity.Cliente;
import br.com.estribadobank.banco.repository.ClienteRepository;
import br.com.estribadobank.banco.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteService clienteService, ClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteCadastrado = clienteService.cadastrarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCadastrado);
        } catch (ClienteException.ClienteJaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o cliente");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> fazLogin(@RequestBody Map<String, String> loginBody) {
        try {
            Cliente cliente = clienteRepository.findClienteByCpfAndSenha(loginBody.get("cpf"), loginBody.get("senha"));

            if (cliente != null) {
                clienteService.logarCliente(cliente);
                clienteRepository.save(cliente);

                return ResponseEntity.status(HttpStatus.OK).body(cliente);
            } else {
                throw new ClienteException.LoginIncorretoException();
            }
        } catch (ClienteException.LoginIncorretoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logoutCliente(@RequestBody Map<String, String> loginBody) {
        try {
            String cpf = loginBody.get("cpf");
            String senha = loginBody.get("senha");
            if (cpf == null || senha == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF e senha são obrigatórios");
            }
            Cliente cliente = clienteRepository.findClienteByCpfAndSenha(cpf, senha);
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
            }
            if (!cliente.isLogado()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cliente já está deslogado");
            }
            clienteService.deslogarCliente(cliente);
            return ResponseEntity.ok("Cliente deslogado com sucesso");
        } catch (ClienteException.ClienteNaoCadastradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao realizar o logout");
        }
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<Object> atualizarCliente(@PathVariable UUID id, @RequestBody Cliente cliente) {
        try {
            clienteService.atualizarCliente(id, cliente);
            return ResponseEntity.ok("Cliente atualizado com sucesso");
        } catch (ClienteException.ClienteNaoCadastradoException | ClienteException.ClienteNaoEstaLogado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o cliente");
        }
    }

    @DeleteMapping("/{id}/remover")
    public ResponseEntity<Object> removerCliente(@PathVariable UUID id) {
        try {
            clienteService.removerCliente(id);
            return ResponseEntity.ok("Cliente removido com sucesso");
        } catch (ClienteException.ClienteNaoCadastradoException | ClienteException.ClienteNaoEstaLogado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover o cliente");
        }
    }

    @PostMapping("/{id}/upgrade-conta")
    public ResponseEntity<Object> upgradeDaConta(@PathVariable UUID id) {
        try {
            clienteService.upgradeDaConta(id);
            return ResponseEntity.ok("Conta atualizada com sucesso");
        } catch (ClienteException.ClienteNaoCadastradoException | ClienteException.ClienteNaoEstaLogado |
                 ClienteException.RendaMinimaException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a conta");
        }
    }

}