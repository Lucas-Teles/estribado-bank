package br.com.estribadobank.banco.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Conta {
    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    private String tipoDeConta;
    private BigDecimal rendaMensal;
    private BigDecimal saldo = BigDecimal.ZERO;
    private BigDecimal limite;
    private BigDecimal transferenciaMaxima;
    private String chavePix;

    private Integer saquesFeitos = 0;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataDeCriacaoDaConta;

    @UpdateTimestamp
    private LocalDateTime dataDeAtualizacaoDaConta;

    protected Conta(){}

    public Conta(Cliente cliente){
        this.cliente = cliente;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipoDeConta() {
        return tipoDeConta;
    }

    public void setTipoDeConta(String tipoDeConta) {
        this.tipoDeConta = tipoDeConta;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public BigDecimal getTransferenciaMaxima() {
        return transferenciaMaxima;
    }

    public void setTransferenciaMaxima(BigDecimal transferenciaMaxima) {
        this.transferenciaMaxima = transferenciaMaxima;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public Integer getSaquesFeitos() {
        return saquesFeitos;
    }

    public void setSaquesFeitos(Integer saquesFeitos) {
        this.saquesFeitos = saquesFeitos;
    }

    public LocalDateTime getDataDeCriacaoDaConta() {
        return dataDeCriacaoDaConta;
    }

    public void setDataDeCriacaoDaConta(LocalDateTime dataDeCriacaoDaConta) {
        this.dataDeCriacaoDaConta = dataDeCriacaoDaConta;
    }

    public LocalDateTime getDataDeAtualizacaoDaConta() {
        return dataDeAtualizacaoDaConta;
    }

    public void setDataDeAtualizacaoDaConta(LocalDateTime dataDeAtualizacaoDaConta) {
        this.dataDeAtualizacaoDaConta = dataDeAtualizacaoDaConta;
    }
}
