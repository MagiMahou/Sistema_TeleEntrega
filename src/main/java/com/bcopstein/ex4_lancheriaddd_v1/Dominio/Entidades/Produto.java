package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

public class Produto {
    private long id;
    private String descricao;
    private Receita receita;
    private double preco;

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