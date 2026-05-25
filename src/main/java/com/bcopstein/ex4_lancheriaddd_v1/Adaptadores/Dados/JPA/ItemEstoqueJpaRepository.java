package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;

public interface ItemEstoqueJpaRepository extends JpaRepository<ItemEstoque, Long> {
}