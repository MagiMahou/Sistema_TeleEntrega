package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;
import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class RecuperaPedidosPendentesUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;

    public RecuperaPedidosPendentesUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
    }

    public List<PedidoResponse> run(String token) {
        if (!authService.isAutenticado(token)) {
            throw new SecurityException("Acesso negado");
        }

        return pedidosRepository.recuperarPendentes().stream()
                .map(p -> new PedidoResponse(p.getId(), p.getStatus().name(), p.getValor(), p.getDesconto(), p.getImpostos(), p.getValorCobrado(), ""))
                .toList();
    }
}