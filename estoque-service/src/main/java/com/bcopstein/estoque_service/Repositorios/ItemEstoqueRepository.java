package com.bcopstein.estoque_service.Repositorios;

import com.bcopstein.estoque_service.Entidades.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
    ItemEstoque findByIngredienteId(Long ingredienteId);
}