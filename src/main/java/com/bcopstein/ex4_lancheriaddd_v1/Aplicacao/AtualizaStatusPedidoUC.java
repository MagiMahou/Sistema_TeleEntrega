package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class AtualizaStatusPedidoUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;
    private final RabbitTemplate rabbitTemplate;!

    public AtualizaStatusPedidoUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService, RabbitTemplate rabbitTemplate) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public PedidoResponse run(long idPedido, String novoStatus, String token) {
        if (!authService.possuiPerfil(token, Perfil.COZINHA)) {
            throw new SecurityException("Acesso negado: Apenas a cozinha pode alterar o status.");
        }
        
        Pedido pedido = pedidosRepository.recuperarPorId(idPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        Pedido.Status statusEnum = Pedido.Status.valueOf(novoStatus.toUpperCase());
        pedido.setStatus(statusEnum);
        pedidosRepository.atualizar(pedido);


        if (statusEnum == Pedido.Status.PRONTO) {
            String mensagem = "O Pedido ID " + pedido.getId() + " está pronto para ser entregue!";
            rabbitTemplate.convertAndSend("pedidos.prontos.fila", mensagem);
            System.out.println("📢 [PIZZARIA] Mensagem disparada para a fila: " + mensagem);
        }

        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(), pedido.getValor(), pedido.getDesconto(), pedido.getImpostos(), pedido.getValorCobrado(), "");
    }
}