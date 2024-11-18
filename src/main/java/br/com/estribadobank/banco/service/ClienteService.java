package br.com.estribadobank.banco.service;

import br.com.estribadobank.banco.Exception.ClienteException;
import br.com.estribadobank.banco.model.entity.Cliente;
import br.com.estribadobank.banco.model.entity.Conta;
import br.com.estribadobank.banco.model.entity.ContaCorrente;
import br.com.estribadobank.banco.model.entity.ContaPagamento;
import br.com.estribadobank.banco.repository.ClienteRepository;
import br.com.estribadobank.banco.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ClienteService(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        boolean clienteCadastrado = clienteRepository.existsByNomeAndCpf(cliente.getNome(), cliente.getCpf());
        if (clienteCadastrado) {
            throw new ClienteException.ClienteJaExisteException();
        }
        return clienteRepository.save(cliente);
    }

    public Conta selecionarConta(Cliente cliente) {
        if (cliente.getRendaMensal().compareTo(new BigDecimal("2118.00")) >= 0) {
            return new ContaCorrente(cliente);
        }
        return new ContaPagamento(cliente);
    }

    public void removerConta(Cliente cliente) {
        Conta conta = contaRepository.findByCliente(cliente);
        if (conta != null){
            contaRepository.delete(conta);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void removerCliente(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(ClienteException.ClienteNaoCadastradoException::new);

        if (!cliente.isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        removerConta(cliente);
        clienteRepository.delete(cliente);
    }

    public void atualizarCliente(UUID id, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(ClienteException.ClienteNaoCadastradoException::new);

        if (!cliente.isLogado()){
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        clienteExistente.setTelefone(cliente.getTelefone());
        clienteExistente.setEndereco(cliente.getEndereco());
        clienteExistente.setRendaMensal(cliente.getRendaMensal());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setSenha(cliente.getSenha());

        if (cliente.getRendaMensal().compareTo(new BigDecimal("2118.00")) >= 0) ofertarUpgradeDaConta(clienteExistente);

        clienteRepository.save(clienteExistente);
    }


    public void mudarSenha(Cliente cliente, String senha) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            cliente.setSenha(senha);
            clienteRepository.save(cliente);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void ofertarUpgradeDaConta(Cliente cliente) {
        System.out.println("Upgrade de conta disponivel, redirecionar cliente para caso o mesmo queira /upgrade-de-conta");
    }

    public void upgradeDaConta(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(ClienteException.ClienteNaoCadastradoException::new);

        if (!cliente.isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        if (cliente.getRendaMensal().compareTo(new BigDecimal("2118.00")) < 0) {
            throw new ClienteException.RendaMinimaException();
        }

        Conta contaAntiga = contaRepository.findByCliente(cliente);
        if (contaAntiga != null) {
            contaRepository.delete(contaAntiga);
        }

        Conta contaNova = new ContaCorrente(cliente);
        contaRepository.save(contaNova);
    }

    public void logarCliente(Cliente cliente) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(ClienteException.ClienteNaoCadastradoException::new);


        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            cliente.setLogado(true);
            clienteRepository.save(cliente);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void deslogarCliente(Cliente cliente) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            cliente.setLogado(false);
            clienteRepository.save(cliente);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }
}