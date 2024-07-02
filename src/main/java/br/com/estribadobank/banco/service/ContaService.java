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
import java.time.temporal.TemporalAdjusters;
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

        return transacaoRepository.findTransacaosByIdClienteAndDataDaTransacaoBetween(id, dataInicial, dataFinal);
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

    public void realizarSaque(UUID id, BigDecimal valorDoSaque) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaException.ContaNaoExisteException());

        if (!conta.getCliente().isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        BigDecimal taxaDeSaque = BigDecimal.ZERO;
        LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fimMes = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

        List<Transacao> saquesFeitosNoMes = transacaoRepository.findTransacaosByTipoDeTransferenciaContainingIgnoreCaseAndIdClienteAndDataDaTransacaoBetween(
                "saque", id, inicioMes, fimMes);

        if (saquesFeitosNoMes.size() >= 4) {
            taxaDeSaque = new BigDecimal("6.50");
        }

        BigDecimal totalSaque = valorDoSaque.add(taxaDeSaque);
        if (conta.getLimite().compareTo(totalSaque) >= 0) {
            conta.setSaldo(conta.getSaldo().subtract(totalSaque));
            conta.atualizarLimite();

            Transacao transacao = new Transacao(conta.getId(), "saque", LocalDateTime.now(), valorDoSaque);
            transacaoRepository.save(transacao);

            contaRepository.save(conta);
        } else {
            throw new ContaException.SemLimiteException();
        }
    }

    public void realizarPagamento(UUID id, BigDecimal valorAPagar) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaException.ContaNaoExisteException());
        if (!conta.getCliente().isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }
        if (conta.getLimite().compareTo(valorAPagar) >= 0) {
            conta.setSaldo(conta.getSaldo().subtract(valorAPagar));
            conta.atualizarLimite();
            Transacao transacao = new Transacao(conta.getId(), "pagamento", LocalDateTime.now(), valorAPagar);
            transacaoRepository.save(transacao);
            contaRepository.save(conta);
        } else {
            throw new ContaException.SemLimiteException();
        }
    }

    public void realizarTransferencia(UUID idContaOrigem, UUID idContaDestino, BigDecimal valorDaTransferencia) {
        Conta contaOrigem = contaRepository.findById(idContaOrigem)
                .orElseThrow(() -> new ContaException.ContaNaoExisteException());

        Conta contaDestino = contaRepository.findById(idContaDestino)
                .orElseThrow(() -> new ContaException.ContaNaoExisteException());

        if (!contaOrigem.getCliente().isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        if (contaOrigem.getLimite().compareTo(valorDaTransferencia) <= 0) {
            throw new ContaException.SemLimiteException();
        }

        boolean isTransferenciaPermitida = contaOrigem.getTipoDeConta().equals("Conta Corrente") ||
                (contaOrigem.getTipoDeConta().equals("Conta Pagamento") &&
                        valorDaTransferencia.compareTo(contaOrigem.getTransferenciaMaxima()) <= 0);

        if (!isTransferenciaPermitida) {
            throw new ContaException.ContaSemPermissaoException();
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valorDaTransferencia));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valorDaTransferencia));

        contaOrigem.atualizarLimite();
        contaDestino.atualizarLimite();

        Transacao transacao = new Transacao(contaOrigem.getId(), "transferência bancária",
                LocalDateTime.now(), valorDaTransferencia, contaDestino.getId());
        transacaoRepository.save(transacao);
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
    }

    public void transferenciaViaPix(UUID idContaOrigem, String chavePix, BigDecimal valorDoPix) {
        Conta contaOrigem = contaRepository.findById(idContaOrigem)
                .orElseThrow(() -> new ContaException.ContaNaoExisteException());

        Conta contaDestino = contaRepository.findByChavePix(chavePix);
        if (contaDestino == null) {
            throw new ContaException.ChavePixInvalidaException();
        }

        if (!contaOrigem.getCliente().isLogado()) {
            throw new ClienteException.ClienteNaoEstaLogado();
        }

        if (contaOrigem.getSaldo().compareTo(valorDoPix) < 0) {
            throw new ContaException.SemLimiteException();
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valorDoPix));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valorDoPix));

        contaOrigem.atualizarLimite();
        contaDestino.atualizarLimite();

        Transacao transacao = new Transacao(contaOrigem.getId(), "Transferência via Pix",
                LocalDateTime.now(), valorDoPix, contaDestino.getId());
        transacaoRepository.save(transacao);
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
    }

    public void cadastrarChavePix(UUID id, String chavePix){
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new ContaException.ContaNaoExisteException());
        if (!conta.getCliente().isLogado()) {throw new ClienteException.ClienteNaoEstaLogado();}

        conta.setChavePix(chavePix);
        contaRepository.save(conta);
    }

    public void removerChavePix(UUID id){
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new ContaException.ContaNaoExisteException());
        if (!conta.getCliente().isLogado()) {throw new ClienteException.ClienteNaoEstaLogado();}

        conta.setChavePix(null);
        contaRepository.save(conta);
    }

    public BigDecimal calcularLimite(Conta conta){
        BigDecimal percentual = new BigDecimal("0.15");
        BigDecimal parteRendaMensal = conta.getRendaMensal().multiply(percentual);
        return conta.getSaldo().add(parteRendaMensal);
    }
}
