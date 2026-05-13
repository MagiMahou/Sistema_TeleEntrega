package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmeterPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.SubmeterPedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private SubmeterPedidoUC submeterPedidoUC;

    @Autowired
    public PedidoController(SubmeterPedidoUC submeterPedidoUC) {
        this.submeterPedidoUC = submeterPedidoUC;
    }

    @PostMapping("/submeter")
    @CrossOrigin("*")
    public ResponseEntity<PedidoResponse> submeterPedido(@RequestBody SubmeterPedidoRequest request) {
        try {
            PedidoResponse response = submeterPedidoUC.run(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}