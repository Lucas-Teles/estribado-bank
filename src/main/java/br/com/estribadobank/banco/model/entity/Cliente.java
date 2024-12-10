package br.com.estribadobank.banco.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "cliente")
public class Cliente {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "CPF não pode ser vazio")
    @Column(nullable = false, unique = true)
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "Nome não pode ser vazio")
    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String nome;

//    @NotBlank(message = "Data de nascimento não pode ser vazia")
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    @NotBlank(message = "Telefone não pode ser vazio")
    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "Endereço não pode ser vazio")
    @Column(nullable = false)
    @Size(min = 5, max = 255)
    private String endereco;

    @NotNull(message = "E-mail não pode ser vazio")
    @Column(nullable = false)
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha não pode ser vazia")
    @Column(nullable = false)
    @Size(min = 8, max = 100)
    private String senha;

    @NotNull(message = "Renda mensal não pode ser vazia")
    @Column(nullable = false)
    @Digits(integer = 10, fraction = 2, message = "Renda informada não é válida. Por favor, insira um valor numérico válido")
    private BigDecimal rendaMensal;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;
}
