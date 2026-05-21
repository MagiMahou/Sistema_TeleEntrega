package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CadastrarClienteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.AutenticarClienteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.CadastrarClienteRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.LoginRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.LoginResponse;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final CadastrarClienteUC cadastrarClienteUC;
    private final AutenticarClienteUC autenticarClienteUC;

    public ClienteController(CadastrarClienteUC cadastrarClienteUC, AutenticarClienteUC autenticarClienteUC) {
        this.cadastrarClienteUC = cadastrarClienteUC;
        this.autenticarClienteUC = autenticarClienteUC;
    }

    @PostMapping("/cadastro")
    @CrossOrigin("*")
    public ResponseEntity<Object> cadastrar(@RequestBody CadastrarClienteRequest request) {
        try {
            cadastrarClienteUC.run(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente registrado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = autenticarClienteUC.run(request);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}