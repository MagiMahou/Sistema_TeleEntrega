package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido.Status;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class CancelarPedidoUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;

    public CancelarPedidoUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
    }

    public PedidoResponse run(long idPedido, String token) {
        if (!authService.isAutenticado(token)) {
            throw new SecurityException("Acesso negado");
        }

        Pedido pedido = pedidosRepository.recuperarPorId(idPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        // Regra de Negócio: Somente pedidos APROVADOS (e não pagos) podem ser cancelados
        if (pedido.getStatus() != Pedido.Status.APROVADO) {
            throw new IllegalStateException("Apenas pedidos aprovados e aguardando pagamento podem ser cancelados.");
        }

        pedido.setStatus(Pedido.Status.CANCELADO);
        pedidosRepository.atualizar(pedido);

        // Retorna o pedido atualizado
        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(), pedido.getValor(), 
                                  pedido.getDesconto(), pedido.getImpostos(), pedido.getValorCobrado(), "");
    }
}