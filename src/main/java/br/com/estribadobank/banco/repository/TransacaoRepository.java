package br.com.estribadobank.banco.repository;

import br.com.estribadobank.banco.model.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    List<Transacao> findTransacaoByTipoDeTransferenciaContainingIgnoreCase(String tipoDeTransferencia);
    List<Transacao> findTransacaosByIdClienteAndDataDaTransacaoBetween(UUID idCliente, LocalDateTime dataInicial, LocalDateTime dataFinal);
    List<Transacao> findTransacaosByTipoDeTransferenciaContainingIgnoreCaseAndIdClienteAndDataDaTransacaoBetween(String tipo, UUID idCliente, LocalDateTime dataInicio, LocalDateTime dataFim);
}
