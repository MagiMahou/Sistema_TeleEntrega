package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CardapioService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class RecuperarCardapioUC {
    private final CardapioService cardapioService;
    private final IAutenticacaoService authService; 

    @Autowired
    public RecuperarCardapioUC(CardapioService cardapioService, IAutenticacaoService authService) {
        this.cardapioService = cardapioService;
        this.authService = authService;
    }

    public CardapioResponse run(long idCardapio, String token) {
        if (!authService.isAutenticado(token)) {
            throw new SecurityException("Acesso negado: Cliente não autenticado.");
        }

        var cardapio = cardapioService.recuperaCardapio(idCardapio);
        if (cardapio == null) {
            throw new IllegalArgumentException("Cardápio não encontrado");
        }

        return new CardapioResponse(cardapio, List.of());
    }
}