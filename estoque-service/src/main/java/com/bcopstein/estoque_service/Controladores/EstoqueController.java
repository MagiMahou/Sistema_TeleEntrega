package com.bcopstein.estoque_service.Controladores;

import com.bcopstein.estoque_service.Entidades.ItemEstoque;
import com.bcopstein.estoque_service.Repositorios.ItemEstoqueRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final ItemEstoqueRepository itemEstoqueRepository;

    private final java.util.Map<Long, java.util.List<Long[]>> receitaPizza = new java.util.HashMap<>();

    public EstoqueController(ItemEstoqueRepository itemEstoqueRepository) {
        this.itemEstoqueRepository = itemEstoqueRepository;
        // Receita: {idProduto -> List<[idIngrediente, quantidade]>}
        receitaPizza.put(1L, java.util.Arrays.asList(new Long[]{1L, 1L}, new Long[]{2L, 2L}, new Long[]{3L, 3L})); // Mussarela
        receitaPizza.put(2L, java.util.Arrays.asList(new Long[]{1L, 1L}, new Long[]{2L, 2L}, new Long[]{4L, 2L})); // Calabresa
        receitaPizza.put(3L, java.util.Arrays.asList(new Long[]{1L, 1L}, new Long[]{2L, 3L}, new Long[]{3L, 2L}, new Long[]{5L, 6L})); // Marguerita
    }

    @GetMapping
    public List<ItemEstoque> listarEstoqueCompleto() {
        return itemEstoqueRepository.findAll();
    }

    private java.util.Map<Long, Integer> calcularIngredientes(com.bcopstein.estoque_service.DTOs.BaixaEstoqueRequest request) {
        java.util.Map<Long, Integer> ingredientesNecessarios = new java.util.HashMap<>();
        for (com.bcopstein.estoque_service.DTOs.ItemBaixaDTO itemDto : request.getItens()) {
            java.util.List<Long[]> receita = receitaPizza.get(itemDto.getIdProduto());
            if (receita != null) {
                for (Long[] ing : receita) {
                    Long idIngrediente = ing[0];
                    int qtdTotal = ing[1].intValue() * itemDto.getQuantidade();
                    ingredientesNecessarios.put(idIngrediente, ingredientesNecessarios.getOrDefault(idIngrediente, 0) + qtdTotal);
                }
            }
        }
        return ingredientesNecessarios;
    }

    @PostMapping("/verificar")
    public boolean verificarDisponibilidade(@RequestBody com.bcopstein.estoque_service.DTOs.BaixaEstoqueRequest request) {
        java.util.Map<Long, Integer> ingredientesNecessarios = calcularIngredientes(request);
        for (java.util.Map.Entry<Long, Integer> entry : ingredientesNecessarios.entrySet()) {
            ItemEstoque item = itemEstoqueRepository.findByIngredienteId(entry.getKey());
            if (item == null || item.getQuantidade() < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @PostMapping("/baixa")
    public void darBaixa(@RequestBody com.bcopstein.estoque_service.DTOs.BaixaEstoqueRequest request) {
        java.util.Map<Long, Integer> ingredientesNecessarios = calcularIngredientes(request);
        for (java.util.Map.Entry<Long, Integer> entry : ingredientesNecessarios.entrySet()) {
            ItemEstoque item = itemEstoqueRepository.findByIngredienteId(entry.getKey());
            if (item != null && item.getQuantidade() >= entry.getValue()) {
                item.setQuantidade(item.getQuantidade() - entry.getValue());
                itemEstoqueRepository.save(item);
            }
        }
    }
}