package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.GerarRelatorioFaturamentoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarPoliticasDescontoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ObterPoliticaDescontoCorrenteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.DefinirPoliticaDescontoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.RelatorioFaturamentoResponse;

@RestController
@RequestMapping("/admin")
public class AdministradorController {
    
    private final GerarRelatorioFaturamentoUC gerarRelatorioFaturamentoUC;
    private final ListarPoliticasDescontoUC listarPoliticasDescontoUC;
    private final ObterPoliticaDescontoCorrenteUC obterPoliticaDescontoCorrenteUC;
    private final DefinirPoliticaDescontoUC definirPoliticaDescontoUC;

    public AdministradorController(GerarRelatorioFaturamentoUC gerarRelatorioFaturamentoUC, 
                                   ListarPoliticasDescontoUC listarPoliticasDescontoUC,
                                   ObterPoliticaDescontoCorrenteUC obterPoliticaDescontoCorrenteUC,
                                   DefinirPoliticaDescontoUC definirPoliticaDescontoUC) {
        this.gerarRelatorioFaturamentoUC = gerarRelatorioFaturamentoUC;
        this.listarPoliticasDescontoUC = listarPoliticasDescontoUC;
        this.obterPoliticaDescontoCorrenteUC = obterPoliticaDescontoCorrenteUC;
        this.definirPoliticaDescontoUC = definirPoliticaDescontoUC;
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
        return ResponseEntity.ok(listarPoliticasDescontoUC.run());
    }

    @GetMapping("/descontos/corrente")
    public ResponseEntity<String> getPoliticaCorrente() {
        return ResponseEntity.ok(obterPoliticaDescontoCorrenteUC.run());
    }

    @PatchMapping("/descontos/corrente")
    public ResponseEntity<String> definirPolitica(@RequestParam String codigo) {
        try {
            definirPoliticaDescontoUC.run(codigo);
            return ResponseEntity.ok("Política de desconto alterada para: " + codigo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}