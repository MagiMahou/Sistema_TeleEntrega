package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;
import java.util.List;

@Component
public class RecuperaPedidosProntosUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;

    public RecuperaPedidosProntosUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
    }

    public List<PedidoResponse> run(String token) {
        if (!authService.possuiPerfil(token, Perfil.ENTREGADOR)) {
            throw new SecurityException("Acesso negado: Apenas entregadores podem consultar esta lista.");
        }

        return pedidosRepository.recuperarProntosParaEntrega().stream()
                .map(pedido -> new PedidoResponse(pedido.getId(), pedido.getStatus().name(),
                        pedido.getValor(), pedido.getDesconto(), pedido.getImpostos(),
                        pedido.getValorCobrado(), ""))
                .toList();
    }
}