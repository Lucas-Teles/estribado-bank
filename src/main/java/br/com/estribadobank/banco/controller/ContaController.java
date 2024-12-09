package br.com.estribadobank.banco.controller;

import br.com.estribadobank.banco.Exception.ClienteException;
import br.com.estribadobank.banco.Exception.ContaException;
import br.com.estribadobank.banco.model.entity.Conta;
import br.com.estribadobank.banco.model.entity.Transacao;
import br.com.estribadobank.banco.repository.ContaRepository;
import br.com.estribadobank.banco.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/conta")
public class ContaController {

}
