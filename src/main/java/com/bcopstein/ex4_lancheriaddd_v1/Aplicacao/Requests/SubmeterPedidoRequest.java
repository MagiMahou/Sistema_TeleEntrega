package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests;

import java.util.List;

public record SubmeterPedidoRequest(List<ItemRequest> itens) {
    public record ItemRequest(long produtoId, int quantidade) {}
}