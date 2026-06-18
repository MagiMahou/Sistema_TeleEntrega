package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.ProdutoJpaRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Repository
public class ProdutosRepositoryImpl implements ProdutosRepository {

    private final ProdutoJpaRepository produtoJpaRepository;

    public ProdutosRepositoryImpl(ProdutoJpaRepository produtoJpaRepository) {
        this.produtoJpaRepository = produtoJpaRepository;
    }

    @Override
    public Produto recuperaProdutoPorid(long id) {
        return produtoJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Produto> recuperaProdutosCardapio(long id) {
        // Mock implementation since Cardapio was removed
        return produtoJpaRepository.findAll();
    }
}
