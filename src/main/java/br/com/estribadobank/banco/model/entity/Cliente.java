package br.com.estribadobank.banco.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "CPF não pode ser vazio")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato inserido incorreto. Por favor, utilize o formato 123.456.789-01")
    private String cpf;

    @NotBlank(message = "Nome não pode ser vazio")
    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String nome;

    @NotNull(message = "Data de nascimento não pode ser vazia")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "Telefone não pode ser vazio")
    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "Endereço não pode ser vazio")
    @Column(nullable = false)
    @Size(min = 5, max = 255)
    private String endereco;

    @NotNull(message = "Renda mensal não pode ser vazia")
    @Column(nullable = false)
    @Digits(integer = 10, fraction = 2, message = "Renda informada não é válida. Por favor, insira um valor numérico válido")
    private BigDecimal rendaMensal;

    @NotBlank(message = "E-mail não pode ser vazio")
    @Column(nullable = false)
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha não pode ser vazia")
    @Column(nullable = false)
    @Size(min = 8, max = 100)
    private String senha;

    @Column(nullable = false)
    private boolean logado = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    protected Cliente() {}

    public Cliente(String cpf, String nome, LocalDate dataNascimento, String telefone, String endereco, BigDecimal rendaMensal, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.rendaMensal = rendaMensal;
        this.email = email;
        this.senha = senha;
        System.out.println("Cliente criado com sucesso!");
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
