package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cardapios")
public class Cardapio {
    
    @Id
    private long id;
    
    @Column(name = "titulo")
    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "cardapio_produto",
        joinColumns = @JoinColumn(name = "cardapio_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos;
    protected Cardapio() {}

    public Cardapio(CabecalhoCardapio cabecalhoCardapio, List<Produto> produtos) {
        this.id = cabecalhoCardapio.id();
        this.titulo = cabecalhoCardapio.titulo();
        this.produtos = produtos;
    }

    public CabecalhoCardapio getCabecalhoCardapio() { 
        return new CabecalhoCardapio(this.id, this.titulo); 
    }
    
    public List<Produto> getProdutos() { 
        return produtos; 
    }
    
    public void setProdutos(List<Produto> produtos) { 
        this.produtos = produtos; 
    }
}