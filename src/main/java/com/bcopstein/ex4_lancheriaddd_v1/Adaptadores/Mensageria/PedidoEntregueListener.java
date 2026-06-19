package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Mensageria;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoEntregueListener {

    private final IPedidosRepository pedidosRepository;

    public PedidoEntregueListener(IPedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }

    @RabbitListener(queuesToDeclare = @Queue("pedidos.entregues.fila"))
    public void receberAvisoDePedidoEntregue(String mensagem) {
        System.out.println("=================================================");
        System.out.println("🍕 [PIZZARIA-SERVICE] AVISO DE ENTREGA RECEBIDO:");
        System.out.println(mensagem);

        try {
            // Extrair o ID do pedido da mensagem (ex: "Pedido 5 foi entregue")
            String idString = mensagem.replaceAll("[^0-9]", "");
            Long pedidoId = Long.parseLong(idString);

            Pedido pedido = pedidosRepository.recuperarPorId(pedidoId);
            if (pedido != null) {
                pedido.setStatus(Pedido.Status.ENTREGUE);
                pedidosRepository.atualizar(pedido);
                System.out.println("✅ Status do Pedido " + pedidoId + " atualizado para ENTREGUE no banco principal!");
            } else {
                System.out.println("⚠️ Pedido " + pedidoId + " não encontrado na base de dados da pizzaria.");
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao processar mensagem de entrega: " + e.getMessage());
        }
        System.out.println("=================================================");
    }
}
