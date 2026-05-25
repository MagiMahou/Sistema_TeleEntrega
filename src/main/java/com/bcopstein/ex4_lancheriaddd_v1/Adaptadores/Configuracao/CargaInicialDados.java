package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Configuracao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Component
public class CargaInicialDados implements CommandLineRunner {

    private final IClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;

    public CargaInicialDados(IClientesRepository clientesRepository, ProdutosRepository produtosRepository) {
        this.clientesRepository = clientesRepository;
        this.produtosRepository = produtosRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO CARGA DE DADOS PARA TESTES ---");

        if (clientesRepository.recuperarPorEmail("gabriel@email.com") == null) {
            Cliente cliente = new Cliente("12345678900", "Gabriel Tavares", "gabriel@email.com", "PUCRS", "senha123");
            clientesRepository.salvar(cliente);
            System.out.println("> Cliente de teste criado: gabriel@email.com / senha123");
        }
        if (produtosRepository.recuperaProdutoPorid(1L) == null) {
            Produto p1 = new Produto(1L, "Pizza Margherita", 45.0); 
            Produto p2 = new Produto(2L, "Pizza Calabresa", 50.0);
            Produto p3 = new Produto(3L, "Refrigerante 2L", 12.0);
            
            produtosRepository.salvar(p1);
            produtosRepository.salvar(p2);
            produtosRepository.salvar(p3);
            System.out.println("> Cardápio populado com sucesso.");
        }

        System.out.println("--- CARGA DE DADOS CONCLUÍDA ---");
    }
}