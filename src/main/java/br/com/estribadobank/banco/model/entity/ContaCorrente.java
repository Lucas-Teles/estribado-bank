package br.com.estribadobank.banco.model.entity;

import jakarta.persistence.Entity;

@Entity
public class ContaCorrente extends Conta {
    public ContaCorrente(){}

    public ContaCorrente(Cliente cliente){
        super(cliente);
        setTipoDeConta("ContaCorrente");
        setRendaMensal(cliente.getRendaMensal());
    }
}
