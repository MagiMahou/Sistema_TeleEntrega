package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "itensEstoque")
public class ItemEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;

    protected ItemEstoque() {}

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