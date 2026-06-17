package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface IDescontoService {
    double calcularDesconto(Pedido pedido);
    java.util.List<String> listarPoliticasDisponiveis();
    void definirPoliticaCorrente(String codigo);
    String getPoliticaCorrente();
}