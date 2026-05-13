package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

public record PedidoResponse(long id, String status, double valorBase, double desconto, double impostos, double valorFinal, String enderecoEntrega) {}