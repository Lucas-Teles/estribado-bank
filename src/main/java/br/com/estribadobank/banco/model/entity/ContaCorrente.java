package br.com.estribadobank.banco.model.entity;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class ContaCorrente extends Conta {
    public ContaCorrente() {
    }

    public ContaCorrente(Cliente cliente) {
        super(cliente);
        tipoDeConta = "Conta Corrente";
        limite = saldo.add(cliente.getRendaMensal().multiply(new BigDecimal("0.15")));
    }

    @Override
    public void atualizarLimite() {
        Cliente cliente = getCliente();
        setLimite(saldo.add(cliente.getRendaMensal().multiply(new BigDecimal("0.15"))));
    }
}
