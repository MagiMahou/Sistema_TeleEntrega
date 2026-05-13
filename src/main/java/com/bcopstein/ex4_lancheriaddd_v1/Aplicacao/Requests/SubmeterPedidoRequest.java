package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests;

import java.util.List;

public record SubmeterPedidoRequest(
    String token, 
    String cpfCliente, 
    String enderecoEntrega,
    List<ItemPedidoRequest> itens
) {}