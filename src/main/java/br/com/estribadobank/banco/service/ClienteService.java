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
    private final ClienteRepository clienteRepository;

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

    public void removerCliente(UUID clienteId){
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(ClienteException.ClienteNaoCadastradoException::new);

        if (!cliente.isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        removerConta(cliente);
        clienteRepository.delete(cliente);
    }

    public void atualizarCliente(UUID id, Cliente cliente) {
<<<<<<< HEAD
        Optional<Cliente> clienteExistenteOptional = clienteRepository.findById(id);

        if (clienteExistenteOptional.isPresent()) {
            Cliente clienteExistente = clienteExistenteOptional.get();

            if (cliente.isLogado()) {
                // Copiar os atributos do cliente para o cliente existente
                clienteExistente.setTelefone(cliente.getTelefone());
                clienteExistente.setEndereco(cliente.getEndereco());
                clienteExistente.setRendaMensal(cliente.getRendaMensal());
                clienteExistente.setEmail(cliente.getEmail());
                clienteExistente.setSenha(cliente.getSenha());

                // Verificar condição de renda para ofertar upgrade
                if (cliente.getRendaMensal().compareTo(new BigDecimal("2118.00")) >= 0) {
                    ofertarUpgradeDaConta(clienteExistente);
                }


                clienteRepository.save(clienteExistente);
=======
        if (clienteRepository.findById(id).isPresent()) {
            Cliente clienteAtualizado = clienteRepository.findById(id).get();
            if (clienteAtualizado.isLogado()) {
                clienteAtualizado.setTelefone(cliente.getTelefone());
                clienteAtualizado.setEndereco(cliente.getEndereco());
                clienteAtualizado.setRendaMensal(cliente.getRendaMensal());
                clienteAtualizado.setEmail(cliente.getEmail());
                clienteAtualizado.setSenha(cliente.getSenha());
                if (clienteAtualizado.getRendaMensal().compareTo(new BigDecimal("2118.00")) >= 0){
                    ofertarUpgradeDaConta(clienteAtualizado);
                    clienteRepository.save(clienteAtualizado);
                }
                clienteRepository.save(clienteAtualizado);
>>>>>>> 1ca7db0192e7c303ccf87e5c8a721631fea2a3b7
            } else {
                throw new ClienteException.ClienteNaoEstaLogado();
            }
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

<<<<<<< HEAD
=======
    public void mudarSenha(Cliente cliente, String senha) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            cliente.setSenha(senha);
            clienteRepository.save(cliente);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }
>>>>>>> 1ca7db0192e7c303ccf87e5c8a721631fea2a3b7

    public void ofertarUpgradeDaConta(Cliente cliente){
        System.out.println("Upgrade de conta disponivel, redirecionar cliente para caso o mesmo queira /upgrade-de-conta");
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

    @Transactional
    public void logarCliente(Cliente cliente) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            cliente.setLogado(true);
            clienteRepository.save(cliente);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void deslogarCliente(Cliente cliente){
        if (clienteRepository.findById(cliente.getId()).isPresent()){
            cliente.setLogado(false);
            clienteRepository.save(cliente);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }
}
