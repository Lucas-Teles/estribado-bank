package br.com.estribadobank.banco.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @UuidGenerator
    private UUID idTransacao;
    private UUID idCliente;
    private String tipoDeTransferencia;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataDaTransacao;
    private BigDecimal quantia;
    private UUID idClienteDestino;

    protected Transacao(){}

    public Transacao(UUID idCliente, String tipoDeTransferencia, LocalDateTime dataDaTransacao, BigDecimal quantia) {
        this.idCliente = idCliente;
        this.tipoDeTransferencia = tipoDeTransferencia;
        this.dataDaTransacao = dataDaTransacao;
        this.quantia = quantia;
    }

    public Transacao(UUID idCliente, String tipoDeTransferencia, LocalDateTime dataDaTransacao, BigDecimal quantia, UUID idClienteDestino) {
        this.idCliente = idCliente;
        this.tipoDeTransferencia = tipoDeTransferencia;
        this.dataDaTransacao = dataDaTransacao;
        this.quantia = quantia;
        this.idClienteDestino = idClienteDestino;
    }

    public UUID getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(UUID idTransacao) {
        this.idTransacao = idTransacao;
    }

    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoDeTransferencia() {
        return tipoDeTransferencia;
    }

    public void setTipoDeTransferencia(String tipoDeTransferencia) {
        this.tipoDeTransferencia = tipoDeTransferencia;
    }

    public LocalDateTime getDataDaTransacao() {
        return dataDaTransacao;
    }

    public void setDataDaTransacao(LocalDateTime dataDaTransacao) {
        this.dataDaTransacao = dataDaTransacao;
    }

    public BigDecimal getQuantia() {
        return quantia;
    }

    public void setQuantia(BigDecimal quantia) {
        this.quantia = quantia;
    }

    public UUID getIdClienteDestino() {
        return idClienteDestino;
    }

    public void setIdClienteDestino(UUID idClienteDestino) {
        this.idClienteDestino = idClienteDestino;
    }
}
