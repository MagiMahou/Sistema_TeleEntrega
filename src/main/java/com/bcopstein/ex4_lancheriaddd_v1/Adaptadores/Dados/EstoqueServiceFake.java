package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Service
public class EstoqueServiceFake implements IEstoqueService {
    private Estoque estoque;

    public EstoqueServiceFake() {
        List<ItemEstoque> itens = new ArrayList<>();
        itens.add(new ItemEstoque(new Ingrediente(1, "Massa de Pizza"), 100));
        itens.add(new ItemEstoque(new Ingrediente(2, "Porção de Queijo Mussarela"), 100));
        itens.add(new ItemEstoque(new Ingrediente(3, "Porção de Calabresa"), 100));
        itens.add(new ItemEstoque(new Ingrediente(4, "Garrafa de Refrigerante"), 50));
        this.estoque = new Estoque(itens);
    }

    private Map<Long, Integer> calcularNecessidade(Pedido pedido) {
        Map<Long, Integer> necessidade = new HashMap<>();
        for (ItemPedido itemPedido : pedido.getItens()) {
            Produto produto = itemPedido.getProduto();
            int qtdProduto = itemPedido.getQuantidade();

            if (produto.getReceita() != null) {
                for (Ingrediente ing : produto.getReceita().getIngredientes()) {
                    long idIng = ing.getId();
                    necessidade.put(idIng, necessidade.getOrDefault(idIng, 0) + qtdProduto);
                }
            }
        }
        return necessidade;
    }

    @Override
    public boolean verificarDisponibilidade(Pedido pedido) {
        Map<Long, Integer> necessidade = calcularNecessidade(pedido);
        for (Map.Entry<Long, Integer> entry : necessidade.entrySet()) {
            long idIngrediente = entry.getKey();
            int qtdNecessaria = entry.getValue();

            ItemEstoque itemDisp = estoque.getItens().stream()
                .filter(ie -> ie.getIngrediente().getId() == idIngrediente)
                .findFirst()
                .orElse(null);

            if (itemDisp == null || itemDisp.getQuantidade() < qtdNecessaria) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void abaterEstoque(Pedido pedido) {
        Map<Long, Integer> necessidade = calcularNecessidade(pedido);
        for (Map.Entry<Long, Integer> entry : necessidade.entrySet()) {
            long idIngrediente = entry.getKey();
            int qtdNecessaria = entry.getValue();

            estoque.getItens().stream()
                .filter(ie -> ie.getIngrediente().getId() == idIngrediente)
                .findFirst()
                .ifPresent(ie -> ie.reduzirQuantidade(qtdNecessaria));
        }
    }
}