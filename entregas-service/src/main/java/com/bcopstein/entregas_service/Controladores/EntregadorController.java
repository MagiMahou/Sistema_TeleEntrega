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
    public ResponseEntity<Object> login(@RequestBody com.bcopstein.entregas_service.DTOs.LoginRequest request) {
        if ("entregador@lanchonete.com".equals(request.getEmail()) && "entrega123".equals(request.getSenha())) {
            String token = "token-entregador-entregador01-" + System.currentTimeMillis();
            return ResponseEntity.ok(new com.bcopstein.entregas_service.DTOs.LoginResponse(request.getEmail(), token));
        }
        return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Entrega>> listarPendentes() {
        List<Entrega> pendentes = entregaRepository.findByStatus("PENDENTE");
        return ResponseEntity.ok(pendentes);
    }

    @PatchMapping("/{id}/entregue")
    public ResponseEntity<Object> marcarComoEntregue(@PathVariable Long id) {
        return entregaRepository.findById(id).map(entrega -> {
            if ("ENTREGUE".equals(entrega.getStatus())) {
                return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST).body((Object)"Esta entrega já foi finalizada.");
            }
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
        List<Entrega> entregues = entregaRepository.findByStatus("ENTREGUE");
        return ResponseEntity.ok(entregues);
    }

    @GetMapping("/historico-entre-datas")
    public ResponseEntity<List<Entrega>> listarHistoricoEntreDatas(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<Entrega> entregues = entregaRepository.findByDataHoraAtualizacaoBetween(inicio, fim);
        return ResponseEntity.ok(entregues);
    }
}