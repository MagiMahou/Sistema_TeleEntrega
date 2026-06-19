package com.bcopstein.entregas_service.Controladores;

import com.bcopstein.entregas_service.Entidades.Entrega;
import com.bcopstein.entregas_service.Repositorios.EntregaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/entrega")
public class EntregadorController {

    private final EntregaRepository entregaRepository;
    private final RabbitTemplate rabbitTemplate;

    public EntregadorController(EntregaRepository entregaRepository, RabbitTemplate rabbitTemplate) {
        this.entregaRepository = entregaRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Entrega>> listarPendentes() {
        List<Entrega> pendentes = entregaRepository.findByStatus("PENDENTE");
        return ResponseEntity.ok(pendentes);
    }

    @PatchMapping("/{id}/entregue")
    public ResponseEntity<Object> marcarComoEntregue(@PathVariable Long id) {
        return entregaRepository.findById(id).map(entrega -> {
            entrega.setStatus("ENTREGUE");
            entrega.setDataHoraAtualizacao(LocalDateTime.now());
            entregaRepository.save(entrega);
            
            // Avisar o pizzaria-service que o pedido foi entregue
            String mensagem = "Pedido " + entrega.getPedidoId() + " foi entregue";
            rabbitTemplate.convertAndSend("pedidos.entregues.fila", mensagem);
            System.out.println("📦 [ENTREGAS-SERVICE] Evento de entrega disparado: " + mensagem);
            
            return ResponseEntity.ok().body((Object) entrega);
        }).orElse(ResponseEntity.notFound().build());
    }
}