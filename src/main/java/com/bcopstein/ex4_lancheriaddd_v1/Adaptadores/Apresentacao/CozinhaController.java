package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaPedidosPendentesUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.AtualizaStatusPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import java.util.List;

@RestController
@RequestMapping("/cozinha")
public class CozinhaController {
    private final RecuperaPedidosPendentesUC recuperaPedidosPendentesUC;
    private final AtualizaStatusPedidoUC atualizaStatusPedidoUC;
    public CozinhaController(RecuperaPedidosPendentesUC recuperaPedidosPendentesUC,
                             AtualizaStatusPedidoUC atualizaStatusPedidoUC) {
        this.recuperaPedidosPendentesUC = recuperaPedidosPendentesUC;
        this.atualizaStatusPedidoUC = atualizaStatusPedidoUC;
    }



    @GetMapping("/pedidos")
    @CrossOrigin("*")
    public ResponseEntity<Object> getPedidosPendentes(@RequestHeader(value = "token", required = false) String token) {
        try {
            List<PedidoResponse> pedidos = recuperaPedidosPendentesUC.run(token);
            return ResponseEntity.ok(pedidos);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PatchMapping("/pedidos/{id}/status")
    @CrossOrigin("*")
    public ResponseEntity<Object> atualizarStatus(
            @PathVariable long id,
            @RequestParam String status,
            @RequestHeader(value = "token", required = false) String token) {
        try {
            PedidoResponse pedido = atualizaStatusPedidoUC.run(id, status, token);
            return ResponseEntity.ok(pedido);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}