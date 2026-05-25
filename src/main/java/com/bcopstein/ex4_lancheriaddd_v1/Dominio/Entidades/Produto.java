package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    private long id;
    private String descricao;
    private double preco;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
        name = "produto_receita",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "receita_id")
    )
    private Receita receita;

    protected Produto() {}

    public Produto(long id, String descricao, Receita receita, double preco) {
        this.id = id;
        this.descricao = descricao;
        this.receita = receita;
        this.preco = preco;
    }

    public long getId() { return id; }
    public String getDescricao() { return descricao; }
    public Receita getReceita() { return receita; }
    public double getPreco() { return preco; }
}