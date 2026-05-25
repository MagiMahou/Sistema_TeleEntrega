package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.ProdutoJpaRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.CardapioJpaRepository;
import java.util.List;

@Repository
@Primary
public class ProdutosRepositoryImpl implements ProdutosRepository {

    private final ProdutoJpaRepository jpaRepository;
    private final CardapioJpaRepository cardapioJpaRepository;

    public ProdutosRepositoryImpl(ProdutoJpaRepository jpaRepository, CardapioJpaRepository cardapioJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.cardapioJpaRepository = cardapioJpaRepository;
    }

    @Override
    public Produto recuperaProdutoPorid(long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Produto> recuperaProdutosCardapio(long id_cardapio) {
        return cardapioJpaRepository.findById(id_cardapio)
                .map(Cardapio::getProdutos)
                .orElse(List.of()); 
    }
}