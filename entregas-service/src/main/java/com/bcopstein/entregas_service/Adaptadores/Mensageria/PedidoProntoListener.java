package com.bcopstein.entregas_service.Adaptadores.Mensageria;

import com.bcopstein.entregas_service.Entidades.Entrega;
import com.bcopstein.entregas_service.Repositorios.EntregaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoProntoListener {

    private final EntregaRepository entregaRepository;

        public PedidoProntoListener(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    @RabbitListener(queuesToDeclare = @org.springframework.amqp.rabbit.annotation.Queue("pedidos.prontos.fila"))
    public void receberAvisoDePedidoPronto(String mensagem) {
        System.out.println("=================================================");
        System.out.println("🏍️ [ENTREGAS-SERVICE] NOVA MENSAGEM RECEBIDA:");
        System.out.println(mensagem);
        
        try {
            String idString = mensagem.replaceAll("[^0-9]", "");
            Long pedidoId = Long.parseLong(idString);
            
            String containerId = System.getenv("HOSTNAME");
            if (containerId == null) containerId = "Desconhecido";
            String nomeEntregador = "Motoboy-" + containerId;
            
            Entrega novaEntrega = new Entrega(pedidoId, nomeEntregador);
            entregaRepository.save(novaEntrega);
            
            System.out.println("✅ Entrega registada na base de dados (Pedido: " + pedidoId + ") por " + nomeEntregador);
        } catch (Exception e) {
            System.out.println("❌ Erro ao processar e guardar a entrega: " + e.getMessage());
        }
        System.out.println("=================================================");
    }
}