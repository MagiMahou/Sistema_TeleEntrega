package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IDescontoService;

@Component
public class ListarPoliticasDescontoUC {
    private final IDescontoService descontoService;

    public ListarPoliticasDescontoUC(IDescontoService descontoService) {
        this.descontoService = descontoService;
    }

    public List<String> run() {
        return descontoService.listarPoliticasDisponiveis();
    }
}
