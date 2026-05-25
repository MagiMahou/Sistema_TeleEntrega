package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaPedidosProntosUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ConfirmarEntregaUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.AutenticarFuncionarioUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.LoginRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.LoginResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import java.util.List;

@RestController
@RequestMapping("/entregador")
public class EntregadorController {
    private final RecuperaPedidosProntosUC recuperaPedidosProntosUC;
    private final ConfirmarEntregaUC confirmarEntregaUC;
    private final AutenticarFuncionarioUC autenticarFuncionarioUC;

    public EntregadorController(RecuperaPedidosProntosUC recuperaPedidosProntosUC,
                                ConfirmarEntregaUC confirmarEntregaUC,
                                AutenticarFuncionarioUC autenticarFuncionarioUC) {
        this.recuperaPedidosProntosUC = recuperaPedidosProntosUC;
        this.confirmarEntregaUC = confirmarEntregaUC;
        this.autenticarFuncionarioUC = autenticarFuncionarioUC;
    }

    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = autenticarFuncionarioUC.run(request);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/pedidos")
    @CrossOrigin("*")
    public ResponseEntity<Object> getPedidosProntos(@RequestHeader(value = "token", required = false) String token) {
        try {
            List<PedidoResponse> pedidos = recuperaPedidosProntosUC.run(token);
            return ResponseEntity.ok(pedidos);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PatchMapping("/pedidos/{id}/entregar")
    @CrossOrigin("*")
    public ResponseEntity<Object> confirmarEntrega(
            @PathVariable long id,
            @RequestHeader(value = "token", required = false) String token) {
        try {
            PedidoResponse pedido = confirmarEntregaUC.run(id, token);
            return ResponseEntity.ok(pedido);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}