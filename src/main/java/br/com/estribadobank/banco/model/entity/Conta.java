package br.com.estribadobank.banco.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Conta {
    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    protected String tipoDeConta;
    protected BigDecimal rendaMensal;
    protected BigDecimal saldo = BigDecimal.ZERO;
    protected BigDecimal limite;
    protected BigDecimal transferenciaMaxima;
    protected String chavePix;

    protected Integer saquesFeitos = 0;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataDeCriacaoDaConta;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataDeAtualizacaoDaConta;
}
