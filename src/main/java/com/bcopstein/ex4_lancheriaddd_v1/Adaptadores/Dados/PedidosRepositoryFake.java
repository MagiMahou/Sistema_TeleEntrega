package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Repository
public class PedidosRepositoryFake implements IPedidosRepository {
    private final List<Pedido> pedidos = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Pedido salvar(Pedido pedido) {
        pedidos.add(pedido);
        return pedido;
    }

    @Override
    public List<Pedido> recuperarPendentes() {
        return pedidos.stream()
                .filter(p -> p.getStatus() == Pedido.Status.RECEBIDO || p.getStatus() == Pedido.Status.PREPARANDO)
                .toList();
    }

    @Override
    public Pedido recuperarPorId(long id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Pedido atualizar(Pedido pedido) {
        return pedido;
    }
}