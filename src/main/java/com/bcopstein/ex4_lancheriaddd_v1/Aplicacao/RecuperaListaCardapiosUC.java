package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CabecalhoCardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.CardapioRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class RecuperaListaCardapiosUC {
    private final CardapioRepository cardapioRepository;
    private final IAutenticacaoService authService; //
    @Autowired
    public RecuperaListaCardapiosUC(CardapioRepository cardapioRepository, IAutenticacaoService authService) {
        this.cardapioRepository = cardapioRepository;
        this.authService = authService;
    }

    public List<CabecalhoCardapioResponse> run(String token) {
        if (!authService.isAutenticado(token)) {
            throw new SecurityException("Acesso negado: Cliente não autenticado.");
        }

        return cardapioRepository.cardapiosDisponiveis()
            .stream()
            .map(c -> new CabecalhoCardapioResponse(c.id(), c.titulo()))
            .toList();
    }
}