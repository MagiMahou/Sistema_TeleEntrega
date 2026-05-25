package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import java.util.List;

public class Receita {
    private long id;
    private List<Ingrediente> ingredientes;

    public Receita(long id, List<Ingrediente> ingredientes) {
        this.id = id;
        this.ingredientes = ingredientes;
    }

    public long getId() { return id; }
    public List<Ingrediente> getIngredientes() { return ingredientes; }
}