package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IDescontoService;

@Component
public class ObterPoliticaDescontoCorrenteUC {
    private final IDescontoService descontoService;

    public ObterPoliticaDescontoCorrenteUC(IDescontoService descontoService) {
        this.descontoService = descontoService;
    }

    public String run() {
        return descontoService.getPoliticaCorrente();
    }
}
