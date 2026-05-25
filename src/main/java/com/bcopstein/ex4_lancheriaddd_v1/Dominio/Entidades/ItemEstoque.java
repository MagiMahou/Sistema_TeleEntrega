package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

public class ItemEstoque {
    private Ingrediente ingrediente;
    private int quantidade;

    public ItemEstoque(Ingrediente ingrediente, int quantidade) {
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public Ingrediente getIngrediente() { return ingrediente; }
    public int getQuantidade() { return quantidade; }

    public void reduzirQuantidade(int qtd) {
        if (this.quantidade < qtd) {
            throw new IllegalArgumentException("Quantidade insuficiente de: " + ingrediente.getDescricao());
        }
        this.quantidade -= qtd;
    }

    public void adicionarQuantidade(int qtd) {
        this.quantidade += qtd;
    }
}