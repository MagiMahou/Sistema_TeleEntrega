package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.bcopstein.ex4_lancheriaddd_v1.DTOs.BaixaEstoqueRequest;

@FeignClient(name = "estoque-service")
public interface EstoqueFeignClient {

    @PostMapping("/estoque/verificar")
    boolean verificarDisponibilidade(@RequestBody BaixaEstoqueRequest request);

    @PostMapping("/estoque/baixa")
    void darBaixa(@RequestBody BaixaEstoqueRequest request);
}