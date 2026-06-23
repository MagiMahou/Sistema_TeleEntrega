package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import java.util.List;

@Component
public class ConsultarCardapioUC {
    private final ProdutosRepository produtosRepository;

    public ConsultarCardapioUC(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    public List<Produto> run() {
        return produtosRepository.recuperaProdutosCardapio(0);
    }
}
