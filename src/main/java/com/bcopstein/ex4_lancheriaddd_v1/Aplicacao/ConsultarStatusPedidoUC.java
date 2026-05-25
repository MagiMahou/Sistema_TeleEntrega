package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class ConsultarStatusPedidoUC {
    private final IPedidosRepository pedidosRepository;

    public ConsultarStatusPedidoUC(IPedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }

    public PedidoResponse run(long idPedido, String token) {
        if (token == null || token.isEmpty()) {
            throw new SecurityException("Acesso negado: Token ausente.");
        }
        Pedido pedido = pedidosRepository.recuperarPorId(idPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }
        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(), 
            pedido.getValor(), pedido.getDesconto(), pedido.getImpostos(), 
            pedido.getValorCobrado(), pedido.getCliente().getEndereco());
    }
}