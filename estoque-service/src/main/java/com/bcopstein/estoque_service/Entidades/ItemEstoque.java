package com.bcopstein.estoque_service.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Ingrediente ingrediente;

    private int quantidade;

    protected ItemEstoque() {}

    public ItemEstoque(Ingrediente ingrediente, int quantidade) {
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public long getId() { return id; }
    public Ingrediente getIngrediente() { return ingrediente; }
    public int getQuantidade() { return quantidade; }
}