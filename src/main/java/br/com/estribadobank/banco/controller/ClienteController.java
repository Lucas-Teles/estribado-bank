package br.com.estribadobank.banco.controller;

import br.com.estribadobank.banco.Exception.ClienteException;
import br.com.estribadobank.banco.model.entity.Cliente;
import br.com.estribadobank.banco.repository.ClienteRepository;
import br.com.estribadobank.banco.repository.ContaRepository;
import br.com.estribadobank.banco.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteService clienteService, ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> criarConta(@RequestBody Cliente clienteBody){
        try {
            Cliente cliente = clienteService.cadastrarCliente(clienteBody);
            contaRepository.save(clienteService.selecionarConta(cliente));

            return ResponseEntity.status(HttpStatus.CREATED).body("Conta Criada com sucesso.");
        } catch (ClienteException.ClienteJaExisteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }




}
