package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class ConfirmarEntregaUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;

    public ConfirmarEntregaUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
    }

    public PedidoResponse run(long idPedido, String token) {
        if (!authService.possuiPerfil(token, Perfil.ENTREGADOR)) {
            throw new SecurityException("Acesso negado: Apenas entregadores podem confirmar a entrega.");
        }

        Pedido pedido = pedidosRepository.recuperarPorId(idPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        if (pedido.getStatus() != Pedido.Status.PRONTO) {
            throw new IllegalStateException("Apenas pedidos PRONTOS podem ser entregues.");
        }

        pedido.setStatus(Pedido.Status.ENTREGUE);
        pedidosRepository.atualizar(pedido);

        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(),
                pedido.getValor(), pedido.getDesconto(), pedido.getImpostos(),
                pedido.getValorCobrado(), "");
    }
}