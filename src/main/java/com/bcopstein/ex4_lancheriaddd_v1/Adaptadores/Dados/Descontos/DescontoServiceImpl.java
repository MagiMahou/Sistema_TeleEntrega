package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IDescontoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Descontos.CalculoDescontoStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Primary
public class DescontoServiceImpl implements IDescontoService {

    private String politicaCorrente = "NENHUM";
    private final Map<String, CalculoDescontoStrategy> estrategias;

    public DescontoServiceImpl(List<CalculoDescontoStrategy> listaEstrategias) {
        this.estrategias = listaEstrategias.stream()
            .collect(Collectors.toMap(CalculoDescontoStrategy::getCodigo, e -> e));
    }

    @Override
    public double calcularDesconto(Pedido pedido) {
        double subtotal = 0.0;
        if (pedido.getItens() != null) {
            subtotal = pedido.getItens().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
        }

        CalculoDescontoStrategy estrategia = estrategias.get(politicaCorrente);
        if (estrategia == null) {
            return 0.0;
        }

        return estrategia.calcular(pedido, subtotal);
    }

    public List<String> listarPoliticasDisponiveis() {
        return estrategias.keySet().stream().toList();
    }

    public void definirPoliticaCorrente(String codigo) {
        if (!estrategias.containsKey(codigo)) {
            throw new IllegalArgumentException("Código de desconto inexistente: " + codigo);
        }
        this.politicaCorrente = codigo;
    }
    
    public String getPoliticaCorrente() {
        return this.politicaCorrente;
    }
}