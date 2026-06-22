package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IDescontoService;

@Component
public class DefinirPoliticaDescontoUC {
    private final IDescontoService descontoService;

    public DefinirPoliticaDescontoUC(IDescontoService descontoService) {
        this.descontoService = descontoService;
    }

    public void run(String codigo) {
        descontoService.definirPoliticaCorrente(codigo);
    }
}
