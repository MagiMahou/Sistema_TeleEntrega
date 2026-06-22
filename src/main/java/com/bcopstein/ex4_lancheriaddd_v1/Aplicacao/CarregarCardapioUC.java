package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ICardapioService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import java.util.List;

@Component
public class CarregarCardapioUC {
    private final ICardapioService cardapioService;
    private final ProdutosRepository produtosRepository;

    public CarregarCardapioUC(ICardapioService cardapioService, ProdutosRepository produtosRepository) {
        this.cardapioService = cardapioService;
        this.produtosRepository = produtosRepository;
    }

    public CardapioResponse run() {
        CabecalhoCardapio corrente = cardapioService.getCardapioCorrente();
        List<Produto> produtos = produtosRepository.recuperaProdutosCardapio(corrente.id());
        return new CardapioResponse(corrente.id(), corrente.titulo(), produtos);
    }
}
