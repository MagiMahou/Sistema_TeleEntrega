package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.CardapioRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.CardapioJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Primary
public class CardapioRepositoryImpl implements CardapioRepository {

    private final CardapioJpaRepository jpaRepository;
    private final ProdutosRepository produtosRepository;

    public CardapioRepositoryImpl(CardapioJpaRepository jpaRepository, ProdutosRepository produtosRepository) {
        this.jpaRepository = jpaRepository;
        this.produtosRepository = produtosRepository;
    }

    @Override
    public List<CabecalhoCardapio> cardapiosDisponiveis() {
        return jpaRepository.findAll().stream()
                .map(Cardapio::getCabecalhoCardapio)
                .collect(Collectors.toList());
    }

    @Override
    public Cardapio recuperaPorId(long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Produto> indicacoesDoChef() {
        Produto produtoIndicado = produtosRepository.recuperaProdutoPorid(2L);
        if (produtoIndicado != null) {
            return List.of(produtoIndicado);
        }
        return List.of();
    }
}