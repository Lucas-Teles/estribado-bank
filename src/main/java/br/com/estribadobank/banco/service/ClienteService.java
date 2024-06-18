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
import java.util.UUID;

@Service
public class ClienteService {
    private final ContaRepository contaRepository;
    private ClienteRepository clienteRepository;

    public ClienteService(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarCliente(Cliente cliente){
        boolean clienteEstaCadastrado = clienteRepository.existsByNomeAndCpf(cliente.getNome(), cliente.getCpf());
        if (clienteEstaCadastrado){
            throw new ClienteException.ClienteJaExisteException();
        }
        return clienteRepository.save(cliente);
    }

    public Conta selecionarConta(Cliente cliente){
        if (cliente.getRendaMensal().compareTo(new BigDecimal("2118.00")) >= 0){
            return new ContaCorrente(cliente);
        }
        return new ContaPagamento(cliente);
    }

    public void removerConta(Cliente cliente){
        Conta conta = contaRepository.findByCliente(cliente);
        contaRepository.delete(conta);
    }

    public void removerCliente(Cliente cliente){
        if (clienteRepository.findById(cliente.getId()).isPresent()){
            if (cliente.isLogado()){
                removerConta(cliente);
                clienteRepository.deleteById(cliente.getId());
            } else {
                throw new ClienteException.ClienteNaoEstaLogado();
            }
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void atualizarCliente(UUID id, Cliente cliente){
        if (clienteRepository.findById(id).isPresent()){
            if (cliente.isLogado()){
                Cliente clienteAtualizado = cliente;
                clienteAtualizado.setTelefone(cliente.getTelefone());
                clienteAtualizado.setEndereco(cliente.getEndereco());
                clienteAtualizado.setRendaMensal(cliente.getRendaMensal());
                clienteAtualizado.setEmail(cliente.getEmail());
                clienteAtualizado.setSenha(cliente.getSenha());

                if (cliente.getRendaMensal().compareTo(new BigDecimal("2118.00")) >= 0){
                    ofertarUpgradeDaConta(clienteAtualizado);
                }

                clienteRepository.save(clienteAtualizado);
            } else {
                throw new ClienteException.ClienteNaoEstaLogado();
            }
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void ofertarUpgradeDaConta(Cliente cliente){
        System.out.println("Gostaria de atualizar sua conta para a catergoria Conta Corrente?, se sim acesse /upgrade-de-conta");
    }

    @Transactional
    public void upgradeDaConta(UUID id){
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

    public void logarCliente(Cliente cliente){
        if (clienteRepository.findById(cliente.getId()).isPresent()){
            cliente.setLogado(true);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void deslogarCliente(Cliente cliente){
        if (clienteRepository.findById(cliente.getId()).isPresent()){
            cliente.setLogado(false);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }
}
