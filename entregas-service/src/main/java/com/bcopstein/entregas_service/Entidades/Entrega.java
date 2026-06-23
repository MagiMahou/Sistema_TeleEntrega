package com.bcopstein.entregas_service.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long pedidoId; 
    private String status; 
    private LocalDateTime dataHoraAtualizacao;
    private String entregadorResponsavel;

    public Entrega() {}

    public Entrega(Long pedidoId, String entregadorResponsavel) {
        this.pedidoId = pedidoId;
        this.status = "PENDENTE";
        this.dataHoraAtualizacao = LocalDateTime.now();
        this.entregadorResponsavel = entregadorResponsavel;
    }

    public Long getId() { return id; }
    public Long getPedidoId() { return pedidoId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataHoraAtualizacao() { return dataHoraAtualizacao; }
    public void setDataHoraAtualizacao(LocalDateTime dataHoraAtualizacao) { this.dataHoraAtualizacao = dataHoraAtualizacao; }
    public String getEntregadorResponsavel() { return entregadorResponsavel; }
    public void setEntregadorResponsavel(String entregadorResponsavel) { this.entregadorResponsavel = entregadorResponsavel; }
}