package com.bcopstein.estoque_service.Controladores;

import com.bcopstein.estoque_service.Entidades.ItemEstoque;
import com.bcopstein.estoque_service.Repositorios.ItemEstoqueRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}