package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    public enum Status {
        NOVO,
        APROVADO,
        PAGO,
        AGUARDANDO,
        PREPARACAO,
        PRONTO,
        TRANSPORTE,
        ENTREGUE
    }
    private long id;
    private Cliente cliente;
    private LocalDateTime dataHoraPagamento;
    private List<ItemPedido> itens;
    private Status status;
    private double valor;
    private double impostos;
    private double desconto;
    private double valorCobrado;

    public Pedido(long id, Cliente cliente, LocalDateTime dataHoraPagamento, List<ItemPedido> itens,
            Pedido.Status status, double valor, double impostos, double desconto, double valorCobrado) {
        this.id = id;
        this.cliente = cliente;
        this.dataHoraPagamento = dataHoraPagamento;
        this.itens = itens;
        this.status = status;
        this.valor = valor;
        this.impostos = impostos;
        this.desconto = desconto;
        this.valorCobrado = valorCobrado;
    }

    public long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getDataHoraPagamento() {
        return dataHoraPagamento;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public double getValor() {
        return valor;
    }

    public double getImpostos() {
        return impostos;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

  
    private void calcularValorDosItens() {
        this.valor = this.itens.stream()
            .mapToDouble(item -> item.getItem().getPreco() * item.getQuantidade())
            .sum();
    }

    public void fecharCustoDoPedido(double valorDesconto, double valorImposto) {
        calcularValorDosItens();
        
        this.desconto = valorDesconto;
        this.impostos = valorImposto;
        this.valorCobrado = (this.valor - this.desconto) + this.impostos;
    }

    public void aprovar() {
        if (this.status != Status.NOVO) {
            throw new IllegalStateException("Apenas pedidos com status NOVO podem ser aprovados.");
        }
        this.status = Status.APROVADO;
    }
}
