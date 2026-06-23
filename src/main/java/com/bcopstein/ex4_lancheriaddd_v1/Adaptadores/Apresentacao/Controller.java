package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ConsultarCardapioUC consultarCardapioUC;

    public Controller(com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ConsultarCardapioUC consultarCardapioUC) {
        this.consultarCardapioUC = consultarCardapioUC;
    }

    @GetMapping("")
    @CrossOrigin("*")
    public String welcomeMessage() {
        return "Bem Vindo a Pizzaria ECA";
    }

    @GetMapping("/cardapio")
    @CrossOrigin("*")
    public java.util.List<com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto> consultarCardapio() {
        return consultarCardapioUC.run();
    }
}
