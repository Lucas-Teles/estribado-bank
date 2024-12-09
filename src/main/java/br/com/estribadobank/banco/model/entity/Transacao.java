package br.com.estribadobank.banco.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
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
}
