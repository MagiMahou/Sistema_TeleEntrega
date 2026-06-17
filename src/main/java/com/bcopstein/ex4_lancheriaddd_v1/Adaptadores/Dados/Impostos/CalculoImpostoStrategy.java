package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Impostos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface CalculoImpostoStrategy {
    String getIdentificadorLei();
    double calcular(Pedido pedido, double subtotal);
}