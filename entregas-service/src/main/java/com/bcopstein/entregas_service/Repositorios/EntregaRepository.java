package com.bcopstein.entregas_service.Repositorios;

import com.bcopstein.entregas_service.Entidades.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByStatus(String status);
}