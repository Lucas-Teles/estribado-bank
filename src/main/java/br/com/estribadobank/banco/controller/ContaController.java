package br.com.estribadobank.banco.controller;

import br.com.estribadobank.banco.Exception.ClienteException;
import br.com.estribadobank.banco.Exception.ContaException;
import br.com.estribadobank.banco.model.entity.Conta;
import br.com.estribadobank.banco.model.entity.Transacao;
import br.com.estribadobank.banco.repository.ContaRepository;
import br.com.estribadobank.banco.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/conta")
public class ContaController {
    private final ContaRepository contaRepository;
    private final ContaService contaService;

    public ContaController(ContaRepository contaRepository, ContaService contaService) {
        this.contaRepository = contaRepository;
        this.contaService = contaService;
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Object> verConta(@PathVariable UUID idCliente){
        try {
            Conta conta = contaService.verConta(idCliente);
            return ResponseEntity.status(HttpStatus.OK).body(conta);
        } catch (ClienteException.ClienteNaoEstaLogado e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{idConta}/saldo")
    public ResponseEntity<Object> verSaldo(@PathVariable UUID idConta){
        try {
            BigDecimal saldo = contaService.verSaldo(idConta);
            return ResponseEntity.ok("Saldo da conta " + idConta + ": R$ " + saldo);
        } catch (ClienteException.ClienteNaoEstaLogado e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{idConta}/extrato")
    public ResponseEntity<Object> verExtrato(@PathVariable UUID idConta,
                                             @RequestParam (name = "mes", required = true) int mes,
                                             @RequestParam (name = "ano", required = true) int ano){
        LocalDate diaInicial = LocalDate.of(ano, mes, 1);
        LocalDateTime dataInicial = diaInicial.atStartOfDay();

        int diaFinal = YearMonth.of(ano, mes).atEndOfMonth().getDayOfMonth();
        LocalDate dDiaFinal = LocalDate.of(ano, mes, diaFinal);
        LocalDateTime dataFinal = dDiaFinal.atTime(23,59,59);

        try {
            List<Transacao> transacoes = contaService.verExtrato(idConta, dataInicial, dataFinal);
            return ResponseEntity.ok(transacoes);
        } catch (ClienteException.ClienteNaoEstaLogado e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idConta}/deposito")
    public ResponseEntity<Object> fazDeposito(@PathVariable UUID idConta, @RequestBody Map<String, String> depositoBody) {
        try {
            BigDecimal quantia = new BigDecimal(depositoBody.get("quantia"));
            contaService.depositar(idConta, quantia);
            return ResponseEntity.ok(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoEstaLogado e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idConta}/saque")
    public ResponseEntity<Object> fazSaque(@PathVariable UUID idConta, @RequestBody Map<String, String> saqueBody) {
        try {
            BigDecimal quantia = new BigDecimal(saqueBody.get("quantia"));
            contaService.realizarSaque(idConta, quantia);
            return ResponseEntity.ok(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoEstaLogado e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ContaException.SemLimiteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/{idConta}/pagamento-de-conta")
    public ResponseEntity<Object> pagaConta(@PathVariable UUID idConta, @RequestBody Map<String, String> pagamentoBody) {
        try {
            BigDecimal quantia = new BigDecimal(pagamentoBody.get("quantia"));
            contaService.realizarPagamento(idConta, quantia);
            return ResponseEntity.ok(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoEstaLogado e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idContaOrigem}/transferencia")
    public ResponseEntity<Object> fazTransferencia(@PathVariable UUID idContaOrigem, @RequestBody Map<String, String> transferenciaBody) {
        try {
            UUID idContaDestino = UUID.fromString(transferenciaBody.get("idContaDestino"));
            BigDecimal quantia = new BigDecimal(transferenciaBody.get("quantia"));
            contaService.realizarTransferencia(idContaOrigem, idContaDestino, quantia);
            return ResponseEntity.ok(contaRepository.findById(idContaOrigem));
        } catch (ClienteException.ClienteNaoCadastradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ContaException.SemLimiteException | ContaException.ContaSemPermissaoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/{idConta}/pix")
    public ResponseEntity<Object> fazPix(@PathVariable UUID idConta, @RequestBody Map<String, String> pixBody) {
        try {
            String chavePix = pixBody.get("chavePix");
            BigDecimal quantia = new BigDecimal(pixBody.get("quantia"));
            contaService.transferenciaViaPix(idConta, chavePix, quantia);
            return ResponseEntity.ok(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoEstaLogado e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ContaException.SemLimiteException | ContaException.ChavePixInvalidaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{idConta}/pix/cadastro")
    public ResponseEntity<Object> cadastraChavePix(@PathVariable UUID idConta, @RequestBody Map<String, String> chavePixBody) {
        try {
            String chavePix = chavePixBody.get("chavePix");
            contaService.cadastrarChavePix(idConta, chavePix);
            return ResponseEntity.status(HttpStatus.CREATED).body(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoEstaLogado e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
