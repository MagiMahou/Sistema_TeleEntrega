package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaPedidosPendentesUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.AtualizaStatusPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;

@RestController
@RequestMapping("/cozinha/pedidos")
public class CozinhaController {
    private final RecuperaPedidosPendentesUC recuperaPendentesUC;
    private final AtualizaStatusPedidoUC atualizaStatusUC;

    public CozinhaController(RecuperaPedidosPendentesUC recuperaPendentesUC, AtualizaStatusPedidoUC atualizaStatusUC) {
        this.recuperaPendentesUC = recuperaPendentesUC;
        this.atualizaStatusUC = atualizaStatusUC;
    }

    @GetMapping
    @CrossOrigin("*")
    public ResponseEntity<List<PedidoResponse>> getPendentes(@RequestHeader(value = "token", required = false) String token) {
        try {
            return ResponseEntity.ok(recuperaPendentesUC.run(token));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/{id}/status")
    @CrossOrigin("*")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable long id, 
            @RequestParam String status, 
            @RequestHeader(value = "token", required = false) String token) {
        try {
            return ResponseEntity.ok(atualizaStatusUC.run(id, status, token));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}