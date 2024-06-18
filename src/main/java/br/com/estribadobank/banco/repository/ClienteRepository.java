package br.com.estribadobank.banco.repository;

import br.com.estribadobank.banco.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    boolean existsByNomeAndCpf(String nome, String cpf);
    Cliente findClienteByCpfAndSenha(String cpf, String senha);
}