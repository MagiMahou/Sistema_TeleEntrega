package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Impostos;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class ImpostoLei200 implements CalculoImpostoStrategy {
    @Override
    public String getIdentificadorLei() {
        return "LEI-200";
    }

    @Override
    public double calcular(Pedido pedido, double subtotal) {
        return subtotal * 0.15;
    }
}