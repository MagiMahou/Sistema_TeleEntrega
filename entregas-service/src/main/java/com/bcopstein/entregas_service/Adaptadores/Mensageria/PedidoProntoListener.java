package com.bcopstein.entregas_service.Adaptadores.Mensageria;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoProntoListener {

    @RabbitListener(queues = "pedidos.prontos.fila")
    public void receberAvisoDePedidoPronto(String mensagem) {
        System.out.println("=================================================");
        System.out.println("🏍️ [ENTREGAS-SERVICE] NOVA MENSAGEM RECEBIDA:");
        System.out.println(mensagem);
        System.out.println("=================================================");
    }
}