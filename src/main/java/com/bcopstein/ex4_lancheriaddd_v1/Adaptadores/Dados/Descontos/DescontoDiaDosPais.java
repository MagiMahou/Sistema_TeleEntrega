package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Descontos;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class DescontoDiaDosPais implements CalculoDescontoStrategy {
    @Override
    public String getCodigo() {
        return "PromocaoDiaDosPais";
    }

    @Override
    public double calcular(Pedido pedido, double subtotal) {
        if (subtotal > 50.0) {
            return 15.0;
        }
        return 0.0;
    }
}