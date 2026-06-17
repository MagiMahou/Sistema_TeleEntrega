package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Descontos;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class DescontoPromocaoVerao implements CalculoDescontoStrategy {
    @Override
    public String getCodigo() {
        return "PromocaoVerao";
    }

    @Override
    public double calcular(Pedido pedido, double subtotal) {
        return subtotal * 0.10;
    }
}