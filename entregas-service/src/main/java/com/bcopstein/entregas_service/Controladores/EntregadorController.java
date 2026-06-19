package com.bcopstein.entregas_service.Controladores;

import com.bcopstein.entregas_service.Entidades.Entrega;
import com.bcopstein.entregas_service.Repositorios.EntregaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/entrega")
public class EntregadorController {

    private final EntregaRepository entregaRepository;

    public EntregadorController(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
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
            return ResponseEntity.ok().body((Object) entrega);
        }).orElse(ResponseEntity.notFound().build());
    }
}