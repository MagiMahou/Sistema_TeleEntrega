package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    public enum Status { 
        NOVO, APROVADO, PAGO, AGUARDANDO, PREPARACAO, PRONTO, TRANSPORTE, ENTREGUE, CANCELADO 
    }

    private long id;
    private Cliente cliente;
    private LocalDateTime data_hora_pagamento;
    private List<ItemPedido> itens;
    private Status status;
    private double valor;
    private double impostos;
    private double desconto;
    private double valorCobrado;

    public Pedido(long id, Cliente cliente, LocalDateTime data_hora_pagamento, List<ItemPedido> itens, 
                  Status status, double valor, double impostos, double desconto, double valorCobrado) {
        this.id = id;
        this.cliente = cliente;
        this.data_hora_pagamento = data_hora_pagamento;
        this.itens = itens;
        this.status = status;
        this.valor = valor;
        this.impostos = impostos;
        this.desconto = desconto;
        this.valorCobrado = valorCobrado;
    }

    public void fecharCustoDoPedido(double descontoAplicado, double impostoAplicado) {
        this.valor = 0.0;
        for (ItemPedido item : itens) {
            this.valor += (item.getProduto().getPreco() * item.getQuantidade());
        }
        this.desconto = descontoAplicado;
        this.impostos = impostoAplicado;
        this.valorCobrado = this.valor + this.impostos - this.desconto;
    }

    public void aprovar() {
        if (this.status != Status.NOVO) {
            throw new IllegalStateException("Apenas pedidos NOVOS podem ser aprovados.");
        }
        this.status = Status.APROVADO;
    }

    public void registrarPagamento() {
        this.status = Status.PAGO;
        this.data_hora_pagamento = LocalDateTime.now();
    }

    public long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public LocalDateTime getDataHoraPagamento() { return data_hora_pagamento; }
    public List<ItemPedido> getItens() { return itens; }
    public Status getStatus() { return status; }
    public double getValor() { return valor; }
    public double getImpostos() { return impostos; }
    public double getDesconto() { return desconto; }
    public double getValorCobrado() { return valorCobrado; }

    public void setStatus(Status status) { this.status = status; }
}