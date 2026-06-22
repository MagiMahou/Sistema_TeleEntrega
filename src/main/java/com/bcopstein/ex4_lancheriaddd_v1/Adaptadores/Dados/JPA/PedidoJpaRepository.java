package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import java.util.List;

public interface PedidoJpaRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(Pedido.Status status);
    List<Pedido> findByStatusIn(List<Pedido.Status> statuses);
}