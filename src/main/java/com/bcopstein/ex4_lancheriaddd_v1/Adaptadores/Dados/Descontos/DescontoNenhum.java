package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Descontos;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class DescontoNenhum implements CalculoDescontoStrategy {
    @Override
    public String getCodigo() {
        return "NENHUM";
    }

    @Override
    public double calcular(Pedido pedido, double subtotal) {
        return 0.0;
    }
}