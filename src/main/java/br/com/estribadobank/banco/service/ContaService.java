package br.com.estribadobank.banco.service;

import br.com.estribadobank.banco.Exception.ClienteException;
import br.com.estribadobank.banco.Exception.ContaException;
import br.com.estribadobank.banco.model.entity.Cliente;
import br.com.estribadobank.banco.model.entity.Conta;
import br.com.estribadobank.banco.model.entity.Transacao;
import br.com.estribadobank.banco.repository.ClienteRepository;
import br.com.estribadobank.banco.repository.ContaRepository;
import br.com.estribadobank.banco.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public Conta verConta(UUID id){
        if (clienteRepository.findById(id).isPresent()){
            Cliente cliente = clienteRepository.findById(id).get();
            if (cliente.isLogado()){
                return contaRepository.findByCliente(cliente);
            } else {
                throw new ClienteException.ClienteNaoEstaLogado();
            }
        }else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public BigDecimal verSaldo(UUID id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ClienteException.ClienteNaoCadastradoException());

        Cliente cliente = conta.getCliente();

        if (!cliente.isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        return conta.getSaldo();
    }

    public List<Transacao> verExtrato(UUID id, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaException.ContaNaoExisteException());

        Cliente cliente = conta.getCliente();

        if (!cliente.isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        return transacaoRepository.findTransacaosByIdContaIsAndDataTransacaoIsBetween(id, dataInicial, dataFinal);
    }

    public void depositar(UUID id, BigDecimal valorParaDeposito) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaException.ContaNaoExisteException());

        if (!conta.getCliente().isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        conta.setSaldo(conta.getSaldo().add(valorParaDeposito));
        conta.atualizarLimite();

        Transacao transacao = new Transacao(conta.getId()
                , "depósito", LocalDateTime.now(), valorParaDeposito);
        transacaoRepository.save(transacao);

        contaRepository.save(conta);
    }

    public void realizarSaque(UUID id, BigDecimal valorSacado){
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaException.ContaNaoExisteException());

        if (!conta.getCliente().isLogado()){
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        BigDecimal taxaDeSaque = BigDecimal.ZERO;
        int mesAtual = LocalDate.now().getMonthValue();
        int anoAtual = LocalDate.now().getYear();
        int ultimoDiaDoMes = YearMonth.of(anoAtual, mesAtual).atEndOfMonth().getDayOfMonth();
        LocalDateTime inicioMes = LocalDate.of(anoAtual, mesAtual, 1).atTime(23,59,59);
        LocalDateTime fimMes = LocalDate.of(anoAtual, mesAtual, 1).atTime(23,59,59);
        Integer saquesFeitos = conta.getSaquesFeitos();

        if (saquesFeitos >= 4){
            taxaDeSaque = new BigDecimal("6.50");
        }




    }



    public BigDecimal calcularLimite(Conta conta){
        BigDecimal percentual = new BigDecimal("0.15");
        BigDecimal parteRendaMensal = conta.getRendaMensal().multiply(percentual);
        return conta.getSaldo().add(parteRendaMensal);
    }
}
