package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs;

public class ItemBaixaDTO {
    private Long ingredienteId;
    private int quantidade;

    public ItemBaixaDTO() {}

    public ItemBaixaDTO(Long ingredienteId, int quantidade) {
        this.ingredienteId = ingredienteId;
        this.quantidade = quantidade;
    }

    public Long getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(Long ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
