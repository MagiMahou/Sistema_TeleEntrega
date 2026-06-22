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

    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity<Object> login(@RequestBody java.util.Map<String, String> request) {
        String email = request.get("email");
        String senha = request.get("senha");
        if ("entregador@lanchonete.com".equals(email) && "entrega123".equals(senha)) {
            String token = "simulated_entregador_token_" + java.util.UUID.randomUUID().toString();
            return ResponseEntity.ok(java.util.Map.of("email", email, "token", token));
        }
        return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body("Credenciais inválidas para o entregador.");
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
            
            String mensagem = "Pedido " + entrega.getPedidoId() + " foi entregue";
            rabbitTemplate.convertAndSend("pedidos.entregues.fila", mensagem);
            System.out.println("📦 [ENTREGAS-SERVICE] Evento de entrega disparado: " + mensagem);
            
            return ResponseEntity.ok().body((Object) entrega);
        }).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/historico")
    public ResponseEntity<List<Entrega>> listarHistorico() {
        List<Entrega> concluidas = entregaRepository.findByStatus("ENTREGUE");
        return ResponseEntity.ok(concluidas);
    }
}