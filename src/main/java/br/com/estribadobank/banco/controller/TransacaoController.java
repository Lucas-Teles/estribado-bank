package br.com.estribadobank.banco.controller;

import br.com.estribadobank.banco.model.entity.Transacao;
import br.com.estribadobank.banco.repository.TransacaoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping("/transacoes")
    public List<Transacao> listaDeTransacoes(){
        return transacaoRepository.findAll();
    }

    @GetMapping("/depositos")
    public List<Transacao> listaDeDepositos(){
        return transacaoRepository.findTransacaoByTipoDeTransferenciaContainingIgnoreCase("depósito");
    }

    @GetMapping("/saques")
    public List<Transacao> listaDeSaques(){
        return transacaoRepository.findTransacaoByTipoDeTransferenciaContainingIgnoreCase("saque");
    }

    @GetMapping("/transferencias")
    public List<Transacao> listaDeTransferencias(){
        return transacaoRepository.findTransacaoByTipoDeTransferenciaContainingIgnoreCase("transferência");
    }

    @GetMapping("/pagamentos")
    public List<Transacao> listaDePagamentos(){
        return transacaoRepository.findTransacaoByTipoDeTransferenciaContainingIgnoreCase("pagamento");
    }

    @GetMapping("/pix")
    public List<Transacao> listaDePix(){
        return transacaoRepository.findTransacaoByTipoDeTransferenciaContainingIgnoreCase("pix");
    }



}
