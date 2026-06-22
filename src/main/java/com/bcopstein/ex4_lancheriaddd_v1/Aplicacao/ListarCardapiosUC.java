package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ICardapioService;

@Component
public class ListarCardapiosUC {
    private final ICardapioService cardapioService;

    public ListarCardapiosUC(ICardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    public List<CabecalhoCardapio> run() {
        return cardapioService.listarCardapiosDisponiveis();
    }
}
