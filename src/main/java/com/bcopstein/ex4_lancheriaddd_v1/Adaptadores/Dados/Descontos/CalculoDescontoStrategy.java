package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Descontos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface CalculoDescontoStrategy {
    String getCodigo();
    double calcular(Pedido pedido, double subtotal);
}