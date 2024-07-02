package br.com.estribadobank.banco.model.entity;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class ContaPagamento extends Conta{
    public ContaPagamento() {
    }

    public ContaPagamento(Cliente cliente) {
        super(cliente);
        tipoDeConta = "Conta Pagamento";
        limite = saldo;
        transferenciaMaxima = new BigDecimal("4999.99");
    }

    @Override
    public void atualizarLimite() {
        setLimite(saldo);
    }
}
