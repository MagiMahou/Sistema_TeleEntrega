package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "estoque-service")
public interface EstoqueFeignClient {

    @GetMapping("/estoque/verificar")
    boolean verificarDisponibilidade(@RequestParam("ingredienteId") Long ingredienteId);
}