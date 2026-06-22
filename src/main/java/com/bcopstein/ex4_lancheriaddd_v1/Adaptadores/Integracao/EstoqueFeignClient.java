package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "estoque-service")
public interface EstoqueFeignClient {

    @PostMapping("/estoque/verificar")
    boolean verificarDisponibilidadeLote(@RequestBody com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs.BaixaEstoqueRequest request);

    @PostMapping("/estoque/abater")
    boolean abaterEstoque(@RequestBody com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs.BaixaEstoqueRequest request);
}