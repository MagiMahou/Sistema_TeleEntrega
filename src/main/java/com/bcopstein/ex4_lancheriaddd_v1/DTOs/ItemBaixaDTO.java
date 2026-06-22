package com.bcopstein.ex4_lancheriaddd_v1.DTOs;

public class ItemBaixaDTO {
    private Long idProduto;
    private int quantidade;

    public ItemBaixaDTO() {}

    public ItemBaixaDTO(Long idProduto, int quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
