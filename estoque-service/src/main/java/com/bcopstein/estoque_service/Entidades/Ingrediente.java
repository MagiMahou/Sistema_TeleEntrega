package com.bcopstein.estoque_service.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    protected Ingrediente() {}

    public Ingrediente(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
}