package com.bcopstein.estoque_service.DTOs;

import java.util.List;

public class BaixaEstoqueRequest {
    private List<ItemBaixaDTO> itens;

    public BaixaEstoqueRequest() {}

    public BaixaEstoqueRequest(List<ItemBaixaDTO> itens) {
        this.itens = itens;
    }

    public List<ItemBaixaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemBaixaDTO> itens) {
        this.itens = itens;
    }
}
