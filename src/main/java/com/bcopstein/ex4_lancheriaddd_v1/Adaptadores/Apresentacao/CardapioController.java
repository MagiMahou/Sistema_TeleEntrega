package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarCardapiosUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.DefinirCardapioCorrenteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CarregarCardapioUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import java.util.List;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {
    
    private final ListarCardapiosUC listarCardapiosUC;
    private final DefinirCardapioCorrenteUC definirCardapioCorrenteUC;
    private final CarregarCardapioUC carregarCardapioUC;

    public CardapioController(ListarCardapiosUC listarCardapiosUC, 
                              DefinirCardapioCorrenteUC definirCardapioCorrenteUC,
                              CarregarCardapioUC carregarCardapioUC) {
        this.listarCardapiosUC = listarCardapiosUC;
        this.definirCardapioCorrenteUC = definirCardapioCorrenteUC;
        this.carregarCardapioUC = carregarCardapioUC;
    }

    @GetMapping("")
    @CrossOrigin("*")
    public ResponseEntity<CardapioResponse> carregarCardapio() {
        return ResponseEntity.ok(carregarCardapioUC.run());
    }

    @GetMapping("/disponiveis")
    @CrossOrigin("*")
    public ResponseEntity<Object> listarDisponiveis(@RequestHeader(value = "token", required = false) String token) {
        try {
            List<CabecalhoCardapio> response = listarCardapiosUC.run();
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PatchMapping("/corrente")
    @CrossOrigin("*")
    public ResponseEntity<Object> definirCorrente(@RequestParam long id, @RequestHeader(value = "token", required = false) String token) {
        try {
            definirCardapioCorrenteUC.run(id, token);
            return ResponseEntity.ok("Cardápio atualizado para: " + id);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
