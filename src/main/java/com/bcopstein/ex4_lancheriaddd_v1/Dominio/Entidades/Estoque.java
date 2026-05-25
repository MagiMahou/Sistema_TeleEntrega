package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import java.util.List;

public class Estoque {
    private List<ItemEstoque> itens;

    public Estoque(List<ItemEstoque> itens) {
        this.itens = itens;
    }

    public List<ItemEstoque> getItens() { return itens; }
}