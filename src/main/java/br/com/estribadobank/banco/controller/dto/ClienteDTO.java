package br.com.estribadobank.banco.controller.dto;

import br.com.estribadobank.banco.model.entity.Cliente;

import java.math.BigDecimal;
import java.util.UUID;

public record ClienteDTO(
        UUID id,
        String nome,
        BigDecimal rendaMensal
) {
    public Cliente mapCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setRendaMensal(this.rendaMensal);
        return cliente;
    }

}
