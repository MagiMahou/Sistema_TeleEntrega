package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.EstoqueFeignClient;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class EstoqueServiceImpl implements IEstoqueService {

    private final EstoqueFeignClient estoqueFeignClient;

    public EstoqueServiceImpl(EstoqueFeignClient estoqueFeignClient) {
        this.estoqueFeignClient = estoqueFeignClient;
    }

    // Mapeamento Hardcoded (Receita): Produto ID -> { Ingrediente ID -> Quantidade }
    private Map<Long, Integer> obterReceitaDoProduto(Long produtoId) {
        Map<Long, Integer> receita = new java.util.HashMap<>();
        if (produtoId == 1L) { // Pizza calabresa
            receita.put(1L, 1); // Massa
            receita.put(3L, 1); // Molho
            receita.put(4L, 2); // Calabresa
        } else if (produtoId == 2L) { // Pizza queijo e presunto
            receita.put(1L, 1); // Massa
            receita.put(3L, 1); // Molho
            receita.put(2L, 2); // Queijo
            receita.put(5L, 2); // Presunto
        } else if (produtoId == 3L) { // Pizza margherita
            receita.put(1L, 1); // Massa
            receita.put(3L, 1); // Molho
            receita.put(2L, 2); // Queijo
            receita.put(6L, 1); // Manjericão
        } else if (produtoId == 4L) { // Coca-Cola
            receita.put(7L, 1);
        } else if (produtoId == 5L) { // Água
            receita.put(8L, 1);
        } else if (produtoId == 6L) { // Guaraná
            receita.put(9L, 1);
        }
        return receita;
    }

    private com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs.BaixaEstoqueRequest construirRequisicao(Pedido pedido) {
        Map<Long, Integer> ingredientesNecessarios = new java.util.HashMap<>();
        
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                Map<Long, Integer> receita = obterReceitaDoProduto(item.getProduto().getId());
                for (Map.Entry<Long, Integer> entry : receita.entrySet()) {
                    Long ingredienteId = entry.getKey();
                    int qtd = entry.getValue() * item.getQuantidade();
                    ingredientesNecessarios.put(ingredienteId, ingredientesNecessarios.getOrDefault(ingredienteId, 0) + qtd);
                }
            }
        }

        java.util.List<com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs.ItemBaixaDTO> lista = new java.util.ArrayList<>();
        for (Map.Entry<Long, Integer> entry : ingredientesNecessarios.entrySet()) {
            lista.add(new com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs.ItemBaixaDTO(entry.getKey(), entry.getValue()));
        }
        
        return new com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs.BaixaEstoqueRequest(lista);
    }

    @Override
    public boolean verificarDisponibilidade(Pedido pedido) {
        try {
            com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs.BaixaEstoqueRequest request = construirRequisicao(pedido);
            if (request.getItens().isEmpty()) return true;
            return estoqueFeignClient.verificarDisponibilidadeLote(request);
        } catch (Exception e) {
            System.out.println("Erro ao contactar o serviço de estoque: " + e.getMessage());
            return false; 
        }
    }

    @Override
    public void abaterEstoque(Pedido pedido) {
        try {
            com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.DTOs.BaixaEstoqueRequest request = construirRequisicao(pedido);
            if (!request.getItens().isEmpty()) {
                boolean sucesso = estoqueFeignClient.abaterEstoque(request);
                if (!sucesso) {
                    throw new RuntimeException("Falha ao abater estoque. Itens insuficientes.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao abater o estoque: " + e.getMessage());
            throw new RuntimeException("Erro de comunicação com o serviço de estoque.", e);
        }
    }
}