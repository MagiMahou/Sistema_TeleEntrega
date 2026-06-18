package com.bcopstein.estoque_service.Repositorios;

import com.bcopstein.estoque_service.Entidades.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}