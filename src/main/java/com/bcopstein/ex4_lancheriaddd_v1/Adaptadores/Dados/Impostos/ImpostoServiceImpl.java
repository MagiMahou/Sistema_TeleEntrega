package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Impostos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IImpostoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.Impostos.CalculoImpostoStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Primary
public class ImpostoServiceImpl implements IImpostoService {

    @Value("${lanchonete.imposto.leiCorrente:LEI-100}")
    private String leiCorrente;

    private final Map<String, CalculoImpostoStrategy> estrategias;

    public ImpostoServiceImpl(List<CalculoImpostoStrategy> listaEstrategias) {
        this.estrategias = listaEstrategias.stream()
            .collect(Collectors.toMap(CalculoImpostoStrategy::getIdentificadorLei, e -> e));
    }

    @Override
    public double calcularImposto(Pedido pedido) {
        double subtotal = 0.0;
        if (pedido.getItens() != null) {
            subtotal = pedido.getItens().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
        }

        CalculoImpostoStrategy estrategia = estrategias.get(leiCorrente);
        if (estrategia == null) {
            throw new IllegalArgumentException("Lei de imposto não configurada ou inexistente: " + leiCorrente);
        }

        return estrategia.calcular(pedido, subtotal);
    }
}