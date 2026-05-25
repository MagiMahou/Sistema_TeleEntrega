package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "receitas")
public class Receita {
    @Id
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "receita_ingrediente",
        joinColumns = @JoinColumn(name = "receita_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientes;

    protected Receita() {}

    public Receita(long id, List<Ingrediente> ingredientes) {
        this.id = id;
        this.ingredientes = ingredientes;
    }

    public long getId() { return id; }
    public List<Ingrediente> getIngredientes() { return ingredientes; }
}