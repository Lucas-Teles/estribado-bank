package br.com.estribadobank.banco.controller;

import br.com.estribadobank.banco.Exception.ClienteException;
import br.com.estribadobank.banco.model.entity.Cliente;
import br.com.estribadobank.banco.repository.ClienteRepository;
import br.com.estribadobank.banco.repository.ContaRepository;
import br.com.estribadobank.banco.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;

    public ClienteController(ClienteService clienteService, ClienteRepository clienteRepository, ContaRepository contaRepository) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteCadastrado = clienteService.cadastrarCliente(cliente);
            contaRepository.save(clienteService.selecionarConta(cliente));
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCadastrado);
        } catch (ClienteException.ClienteJaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o cliente");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginCliente(@RequestBody Map<String, String> loginBody) {
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
<<<<<<< HEAD
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
=======
    public ResponseEntity<Object> logoutCliente(@RequestBody Map<String, String> logoutBody) {
        try {
            Cliente cliente = clienteRepository.findClienteByCpfAndSenha(logoutBody.get("cpf"), logoutBody.get("senha"));
            if (cliente != null){
                clienteService.deslogarCliente(cliente);
                clienteRepository.save(cliente);
                return ResponseEntity.ok("Cliente deslogado com sucesso");
            } else {
                throw new ClienteException.LoginIncorretoException();
            }
>>>>>>> 1ca7db0192e7c303ccf87e5c8a721631fea2a3b7
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o cliente");
        }
    }

    @PatchMapping("/{idCliente}/mudar-senha")
    public ResponseEntity<Object> mudarSenha(@RequestBody Map<String, String> novaSenhaBody){
        try {
            Cliente cliente = clienteRepository.findClienteByCpfAndEmail(novaSenhaBody.get("cpf"), novaSenhaBody.get("email"));
            clienteService.mudarSenha(cliente, novaSenhaBody.get("senha"));
            return ResponseEntity.ok().build();
        } catch (ClienteException.ClienteNaoCadastradoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}/encerrar-conta")
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