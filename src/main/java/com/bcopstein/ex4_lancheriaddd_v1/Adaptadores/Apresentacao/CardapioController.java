package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaListaCardapiosUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperarCardapioUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CabecalhoCardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CardapioResponse;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {
    private RecuperaListaCardapiosUC recuperaListaCardapiosUC;
    private RecuperarCardapioUC recuperarCardapioUC;

    @Autowired
    public CardapioController(RecuperaListaCardapiosUC recuperaListaCardapiosUC,
            RecuperarCardapioUC recuperarCardapioUC) {
        this.recuperaListaCardapiosUC = recuperaListaCardapiosUC;
        this.recuperarCardapioUC = recuperarCardapioUC;
    }

    @GetMapping("/lista")
    @CrossOrigin("*")
    public ResponseEntity<List<CabecalhoCardapioResponse>> recuperaLista(
            @RequestHeader(value = "token", required = false) String token) {
        try {
            return ResponseEntity.ok(recuperaListaCardapiosUC.run(token));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity<CardapioResponse> recuperaCardapio(
            @PathVariable long id, 
            @RequestHeader(value = "token", required = false) String token) {
        try {
            return ResponseEntity.ok(recuperarCardapioUC.run(id, token));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}