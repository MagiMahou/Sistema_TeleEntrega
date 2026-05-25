package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.ProdutoJpaRepository;
import java.util.List;

@Repository
@Primary
public class ProdutosRepositoryImpl implements ProdutosRepository {

    private final ProdutoJpaRepository jpaRepository;

    public ProdutosRepositoryImpl(ProdutoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Produto recuperaProdutoPorid(long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Produto> todos() {
        return jpaRepository.findAll();
    }

    @Override
    public void salvar(Produto produto) {
        jpaRepository.save(produto);
    }
}