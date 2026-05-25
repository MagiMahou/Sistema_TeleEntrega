package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmeterPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CancelarPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.PagarPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.SubmeterPedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final SubmeterPedidoUC submeterPedidoUC;
    private final CancelarPedidoUC cancelarPedidoUC;
    private final PagarPedidoUC pagarPedidoUC;

    public PedidoController(SubmeterPedidoUC submeterPedidoUC, CancelarPedidoUC cancelarPedidoUC, PagarPedidoUC pagarPedidoUC) {
        this.submeterPedidoUC = submeterPedidoUC;
        this.cancelarPedidoUC = cancelarPedidoUC;
        this.pagarPedidoUC = pagarPedidoUC;
    }

    @PostMapping("/submeter")
    @CrossOrigin("*")
    public ResponseEntity<Object> submeterPedido(
            @RequestBody SubmeterPedidoRequest request,
            @RequestHeader(value = "token", required = false) String token) { 
        try {
            PedidoResponse response = submeterPedidoUC.run(request, token); 
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/cancelar")
    @CrossOrigin("*")
    public ResponseEntity<Object> cancelarPedido(
            @PathVariable long id, 
            @RequestHeader(value = "token", required = false) String token) {
        try {
            PedidoResponse response = cancelarPedidoUC.run(id, token);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/pagar")
    @CrossOrigin("*")
    public ResponseEntity<Object> pagarPedido(
            @PathVariable long id, 
            @RequestHeader(value = "token", required = false) String token) {
        try {
            PedidoResponse response = pagarPedidoUC.run(id, token);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}