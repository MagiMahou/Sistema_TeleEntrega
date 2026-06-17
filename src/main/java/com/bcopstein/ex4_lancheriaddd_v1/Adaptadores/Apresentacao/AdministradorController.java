package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.GerarRelatorioFaturamentoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.AutenticarFuncionarioUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.LoginRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.LoginResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.RelatorioFaturamentoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IDescontoService;

@RestController
@RequestMapping("/admin")
public class AdministradorController {
    
    private final GerarRelatorioFaturamentoUC gerarRelatorioFaturamentoUC;
    private final AutenticarFuncionarioUC autenticarFuncionarioUC;

    @Autowired
    private IDescontoService descontoService;

    public AdministradorController(GerarRelatorioFaturamentoUC gerarRelatorioFaturamentoUC, AutenticarFuncionarioUC autenticarFuncionarioUC) {
        this.gerarRelatorioFaturamentoUC = gerarRelatorioFaturamentoUC;
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

    @GetMapping("/relatorio")
    @CrossOrigin("*")
    public ResponseEntity<Object> getRelatorioFaturamento(@RequestHeader(value = "token", required = false) String token) {
        try {
            RelatorioFaturamentoResponse relatorio = gerarRelatorioFaturamentoUC.run(token);
            return ResponseEntity.ok(relatorio);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/descontos")
    public ResponseEntity<List<String>> listarPoliticas() {
        return ResponseEntity.ok(descontoService.listarPoliticasDisponiveis());
    }

    @GetMapping("/descontos/corrente")
    public ResponseEntity<String> getPoliticaCorrente() {
        return ResponseEntity.ok(descontoService.getPoliticaCorrente());
    }

    @PatchMapping("/descontos/corrente")
    public ResponseEntity<String> definirPolitica(@RequestParam String codigo) {
        try {
            descontoService.definirPoliticaCorrente(codigo);
            return ResponseEntity.ok("Política de desconto alterada para: " + codigo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}