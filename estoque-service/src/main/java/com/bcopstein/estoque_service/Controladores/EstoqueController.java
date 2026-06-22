package com.bcopstein.estoque_service.Controladores;

import com.bcopstein.estoque_service.Entidades.ItemEstoque;
import com.bcopstein.estoque_service.Repositorios.ItemEstoqueRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final ItemEstoqueRepository itemEstoqueRepository;

    public EstoqueController(ItemEstoqueRepository itemEstoqueRepository) {
        this.itemEstoqueRepository = itemEstoqueRepository;
    }

    @GetMapping
    public List<ItemEstoque> listarEstoqueCompleto() {
        return itemEstoqueRepository.findAll();
    }

    @PostMapping("/verificar")
    public boolean verificarDisponibilidadeLote(@RequestBody com.bcopstein.estoque_service.DTOs.BaixaEstoqueRequest request) {
        for (com.bcopstein.estoque_service.DTOs.ItemBaixaDTO itemDto : request.getItens()) {
            ItemEstoque item = itemEstoqueRepository.findByIngredienteId(itemDto.getIngredienteId());
            if (item == null || item.getQuantidade() < itemDto.getQuantidade()) {
                return false;
            }
        }
        return true;
    }

    @PostMapping("/abater")
    public boolean abaterEstoque(@RequestBody com.bcopstein.estoque_service.DTOs.BaixaEstoqueRequest request) {
        if (!verificarDisponibilidadeLote(request)) {
            return false;
        }
        for (com.bcopstein.estoque_service.DTOs.ItemBaixaDTO itemDto : request.getItens()) {
            ItemEstoque item = itemEstoqueRepository.findByIngredienteId(itemDto.getIngredienteId());
            item.setQuantidade(item.getQuantidade() - itemDto.getQuantidade());
            itemEstoqueRepository.save(item);
        }
        return true;
    }
}