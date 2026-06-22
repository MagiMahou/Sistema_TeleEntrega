package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ICardapioService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;

@Component
public class DefinirCardapioCorrenteUC {
    private final ICardapioService cardapioService;
    private final IAutenticacaoService authService;

    public DefinirCardapioCorrenteUC(ICardapioService cardapioService, IAutenticacaoService authService) {
        this.cardapioService = cardapioService;
        this.authService = authService;
    }

    public void run(long id, String token) {
        if (!authService.possuiPerfil(token, Perfil.ADMINISTRADOR)) {
            throw new SecurityException("Acesso negado: Apenas administradores podem definir o cardápio corrente.");
        }
        cardapioService.definirCardapioCorrente(id);
    }
}
