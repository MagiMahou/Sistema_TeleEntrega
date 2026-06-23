package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Workers;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaAutomationWorker {

    private final IPedidosRepository pedidosRepository;
    private final RabbitTemplate rabbitTemplate;

    public CozinhaAutomationWorker(IPedidosRepository pedidosRepository, RabbitTemplate rabbitTemplate) {
        this.pedidosRepository = pedidosRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    // Roda a cada 10 segundos
    @Scheduled(fixedRate = 10000)
    public void automatizarCozinha() {
        // 1. Finaliza os pedidos que estão em PREPARACAO
        List<Pedido> emPreparacao = pedidosRepository.recuperarEmPreparacao();
        for (Pedido pedido : emPreparacao) {
            pedido.setStatus(Pedido.Status.PRONTO);
            pedidosRepository.atualizar(pedido);
            
            String mensagem = "O Pedido ID " + pedido.getId() + " está pronto para ser entregue!";
            rabbitTemplate.convertAndSend("pedidos.prontos.fila", mensagem);
            System.out.println("🤖 [COZINHA-WORKER] Pedido " + pedido.getId() + " concluído! Mensagem disparada para a fila de entregas.");
        }

        // 2. Inicia o preparo dos pedidos que estão AGUARDANDO
        List<Pedido> aguardando = pedidosRepository.recuperarAguardando();
        for (Pedido pedido : aguardando) {
            pedido.setStatus(Pedido.Status.PREPARACAO);
            pedidosRepository.atualizar(pedido);
            System.out.println("🤖 [COZINHA-WORKER] Pedido " + pedido.getId() + " entrou em PREPARAÇÃO.");
        }

        // 3. Recebe os pedidos PAGO e manda para AGUARDANDO
        List<Pedido> pagos = pedidosRepository.recuperarPendentes(); // recuperarPendentes fetches PAGO
        for (Pedido pedido : pagos) {
            pedido.setStatus(Pedido.Status.AGUARDANDO);
            pedidosRepository.atualizar(pedido);
            System.out.println("🤖 [COZINHA-WORKER] Pedido " + pedido.getId() + " recebido na cozinha (AGUARDANDO).");
        }
    }
}
