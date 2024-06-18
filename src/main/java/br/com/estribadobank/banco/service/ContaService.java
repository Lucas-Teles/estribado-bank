package br.com.estribadobank.banco.service;

import br.com.estribadobank.banco.model.entity.Conta;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ContaService {
    public BigDecimal calcularLimite(Conta conta){
        BigDecimal percentual = new BigDecimal("0.15");
        BigDecimal parteRendaMensal = conta.getRendaMensal().multiply(percentual);
        return conta.getSaldo().add(parteRendaMensal);
    }
}
