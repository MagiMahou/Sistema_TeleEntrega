package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.*;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.ItemEstoqueJpaRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class EstoqueServiceImpl implements IEstoqueService {

    private final ItemEstoqueJpaRepository jpaRepository;

    public EstoqueServiceImpl(ItemEstoqueJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
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
        List<ItemEstoque> itensNoBanco = jpaRepository.findAll();

        for (Map.Entry<Long, Integer> entry : necessidade.entrySet()) {
            long idIngrediente = entry.getKey();
            int qtdNecessaria = entry.getValue();

            ItemEstoque itemDisp = itensNoBanco.stream()
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
        List<ItemEstoque> itensNoBanco = jpaRepository.findAll();

        for (Map.Entry<Long, Integer> entry : necessidade.entrySet()) {
            long idIngrediente = entry.getKey();
            int qtdNecessaria = entry.getValue();

            itensNoBanco.stream()
                .filter(ie -> ie.getIngrediente().getId() == idIngrediente)
                .findFirst()
                .ifPresent(ie -> {
                    ie.reduzirQuantidade(qtdNecessaria);
                    jpaRepository.save(ie); 
                });
        }
    }
}