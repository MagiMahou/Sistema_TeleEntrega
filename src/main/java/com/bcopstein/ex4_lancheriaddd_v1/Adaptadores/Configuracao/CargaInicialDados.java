package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Configuracao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

@Component
public class CargaInicialDados implements CommandLineRunner {

    private final IClientesRepository clientesRepository;

    public CargaInicialDados(IClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO CARGA DE DADOS PARA TESTES ---");

        if (clientesRepository.recuperarPorEmail("gabriel@email.com") == null) {
            // Construtor correto com os 6 parâmetros exigidos pela tua classe Cliente
            Cliente cliente = new Cliente(
                "12345678900", 
                "Gabriel Tavares", 
                "51999999999", 
                "Avenida Ipiranga, 6681", 
                "gabriel@email.com", 
                "senha123"
            );
            clientesRepository.salvar(cliente);
            System.out.println("> Cliente de teste criado: gabriel@email.com / senha123");
        }

        System.out.println("--- CARGA DE DADOS CONCLUÍDA ---");
    }
}