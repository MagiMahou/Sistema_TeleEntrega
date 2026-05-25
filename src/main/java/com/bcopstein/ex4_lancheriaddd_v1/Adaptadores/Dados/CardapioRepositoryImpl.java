package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.CardapioRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.CardapioJpaRepository;
import java.util.List;

@Repository
@Primary
public class CardapioRepositoryImpl implements CardapioRepository {

    private final CardapioJpaRepository jpaRepository;

    public CardapioRepositoryImpl(CardapioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Cardapio> todos() {
        return jpaRepository.findAll();
    }

    @Override
    public Cardapio recuperaCardapioPorId(long id) {
        return jpaRepository.findById(id).orElse(null);
    }
}