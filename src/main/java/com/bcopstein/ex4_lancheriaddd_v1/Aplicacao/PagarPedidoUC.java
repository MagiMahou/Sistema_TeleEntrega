package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IPagamentoService;

@Component
public class PagarPedidoUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;
    private final IPagamentoService pagamentoService;

    public PagarPedidoUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService, IPagamentoService pagamentoService) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
        this.pagamentoService = pagamentoService;
    }

    public PedidoResponse run(long idPedido, String token) {
        if (!authService.isAutenticado(token)) {
            throw new SecurityException("Acesso negado");
        }

        Pedido pedido = pedidosRepository.recuperarPorId(idPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        if (pedido.getStatus() != Pedido.Status.APROVADO) {
            throw new IllegalStateException("Apenas pedidos aprovados e aguardando pagamento podem ser pagos.");
        }

        if (!pagamentoService.processarPagamento(pedido)) {
            throw new IllegalStateException("Falha ao processar o pagamento.");
        }

        pedido.setStatus(Pedido.Status.PAGO);
        pedidosRepository.atualizar(pedido);

        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(), pedido.getValor(), 
                                  pedido.getDesconto(), pedido.getImpostos(), pedido.getValorCobrado(), "");
    }
}