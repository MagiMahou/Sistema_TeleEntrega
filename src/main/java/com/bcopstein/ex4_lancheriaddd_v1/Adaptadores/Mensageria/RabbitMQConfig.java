package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Mensageria;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String FILA_PEDIDOS_PRONTOS = "pedidos.prontos.fila";

    @Bean
    public Queue filaPedidosProntos() {
        return new Queue(FILA_PEDIDOS_PRONTOS, true); 
    }
}