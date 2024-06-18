package br.com.estribadobank.banco.model.entity;

import jakarta.persistence.Entity;

@Entity
public class ContaPagamento extends Conta{
    public ContaPagamento(){}
    public ContaPagamento(Cliente cliente){
        super(cliente);
        setTipoDeConta("Conta Pagamento");
        setRendaMensal(cliente.getRendaMensal());
    }
}
