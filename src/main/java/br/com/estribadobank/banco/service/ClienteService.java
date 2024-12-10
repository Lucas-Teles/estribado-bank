package br.com.estribadobank.banco.service;

import br.com.estribadobank.banco.Exception.ClienteException;
import br.com.estribadobank.banco.model.entity.Cliente;
import br.com.estribadobank.banco.model.entity.Conta;
import br.com.estribadobank.banco.model.entity.ContaCorrente;
import br.com.estribadobank.banco.model.entity.ContaPagamento;
import br.com.estribadobank.banco.repository.ClienteRepository;
import br.com.estribadobank.banco.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    public Cliente salvar(Cliente cliente){return repository.save(cliente);}

    public void atualizar(Cliente cliente){
        if (cliente.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor esteja salvo na base de dados");
        }
        repository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(UUID id){return repository.findById(id);}

    public void deletarCliente(Cliente cliente){
        repository.delete(cliente);
    }

}