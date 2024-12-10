package br.com.estribadobank.banco.controller;

import br.com.estribadobank.banco.controller.dto.ClienteDTO;
import br.com.estribadobank.banco.model.entity.Cliente;
import br.com.estribadobank.banco.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Void> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        service.salvar(cliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> verCliente(@PathVariable("id") UUID id) {
        Optional<Cliente> clienteOptional = service.buscarPorId(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            ClienteDTO dto = new ClienteDTO(cliente.getId(),
                    cliente.getNome(),
                    cliente.getRendaMensal()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable("id") UUID id) {
        Optional<Cliente> clienteOptional = service.buscarPorId(id);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletarCliente(clienteOptional.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCliente(
            @PathVariable("id") UUID id,
            @RequestBody ClienteDTO dto){

        Optional<Cliente> clienteOptional = service.buscarPorId(id);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var cliente = clienteOptional.get();
        cliente.setNome(dto.nome());
        cliente.setRendaMensal(dto.rendaMensal());

        service.atualizar(cliente);

        return ResponseEntity.noContent().build();
    }
}