package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CadastrarClienteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.CadastrarClienteRequest;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final CadastrarClienteUC cadastrarClienteUC;
    private final com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ValidarLoginUC validarLoginUC;

    public ClienteController(CadastrarClienteUC cadastrarClienteUC, com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ValidarLoginUC validarLoginUC) {
        this.cadastrarClienteUC = cadastrarClienteUC;
        this.validarLoginUC = validarLoginUC;
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

    @PostMapping("/validar-login")
    @CrossOrigin("*")
    public ResponseEntity<Object> validarLogin(@RequestBody com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.ValidarLoginRequest request) {
        try {
            com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.ValidarLoginResponse response = validarLoginUC.run(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}