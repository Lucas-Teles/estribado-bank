package br.com.estribadobank.banco.repository;

import br.com.estribadobank.banco.model.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {

}
