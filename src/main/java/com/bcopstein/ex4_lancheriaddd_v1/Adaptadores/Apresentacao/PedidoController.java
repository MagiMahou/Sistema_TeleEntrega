package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.SubmeterPedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final SubmeterPedidoUC submeterPedidoUC;
    private final CancelarPedidoUC cancelarPedidoUC;
    private final PagarPedidoUC pagarPedidoUC;
    private final ConsultarStatusPedidoUC consultarStatusPedidoUC;
    private final ListarPedidosEntreDatasUC listarPedidosEntreDatasUC;
    private final ListarPedidosClienteEntreDatasUC listarPedidosClienteEntreDatasUC;
    private final ListarPedidosClienteUC listarPedidosClienteUC;

    public PedidoController(SubmeterPedidoUC submeterPedidoUC, CancelarPedidoUC cancelarPedidoUC, 
                            PagarPedidoUC pagarPedidoUC, ConsultarStatusPedidoUC consultarStatusPedidoUC,
                            ListarPedidosEntreDatasUC listarPedidosEntreDatasUC,
                            ListarPedidosClienteEntreDatasUC listarPedidosClienteEntreDatasUC,
                            ListarPedidosClienteUC listarPedidosClienteUC) {
        this.submeterPedidoUC = submeterPedidoUC;
        this.cancelarPedidoUC = cancelarPedidoUC;
        this.pagarPedidoUC = pagarPedidoUC;
        this.consultarStatusPedidoUC = consultarStatusPedidoUC;
        this.listarPedidosEntreDatasUC = listarPedidosEntreDatasUC;
        this.listarPedidosClienteEntreDatasUC = listarPedidosClienteEntreDatasUC;
        this.listarPedidosClienteUC = listarPedidosClienteUC;
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

    @GetMapping("/{id}/status")
    @CrossOrigin("*")
    public ResponseEntity<Object> consultarStatus(
            @PathVariable long id,
            @RequestHeader(value = "token", required = false) String token) {
        try {
            PedidoResponse response = consultarStatusPedidoUC.run(id, token);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/entregures-entre-datas")
    @CrossOrigin("*")
    public ResponseEntity<Object> listarEntreDatas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            @RequestHeader(value = "token", required = false) String token) {
        try {
            List<PedidoResponse> response = listarPedidosEntreDatasUC.run(inicio, fim, token);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{cpf}/entregues-entre-datas")
    @CrossOrigin("*")
    public ResponseEntity<Object> listarClienteEntreDatas(
            @PathVariable String cpf,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            @RequestHeader(value = "token", required = false) String token) {
        try {
            List<PedidoResponse> response = listarPedidosClienteEntreDatasUC.run(cpf, inicio, fim, token);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("/cliente/{cpf}")
    @CrossOrigin("*")
    public ResponseEntity<Object> listarPorCliente(
            @PathVariable String cpf,
            @RequestHeader(value = "token", required = false) String token) {
        try {
            List<PedidoResponse> response = listarPedidosClienteUC.run(cpf, token);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}