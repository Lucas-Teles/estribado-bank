package br.com.estribadobank.banco.controller;

import br.com.estribadobank.banco.repository.ClienteRepository;
import br.com.estribadobank.banco.repository.ContaRepository;
import br.com.estribadobank.banco.service.ClienteService;
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




}
