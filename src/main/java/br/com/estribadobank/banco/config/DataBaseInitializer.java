package br.com.estribadobank.banco.config;

import br.com.estribadobank.banco.repository.ClienteRepository;
import br.com.estribadobank.banco.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer implements CommandLineRunner {
    public static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public DataInitializer(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository){
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        log.info("Alô Profª. Analu !! ");
        log.info("O banco ta conectado :D");
    }
}
