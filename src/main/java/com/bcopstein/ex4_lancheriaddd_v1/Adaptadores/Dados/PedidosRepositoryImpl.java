package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.PedidoJpaRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Repository
public class PedidosRepositoryImpl implements IPedidosRepository {

    private final PedidoJpaRepository pedidoJpaRepository;

    public PedidosRepositoryImpl(PedidoJpaRepository pedidoJpaRepository) {
        this.pedidoJpaRepository = pedidoJpaRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return pedidoJpaRepository.save(pedido);
    }

    @Override
    public List<Pedido> recuperarPendentes() {
        // Mock implementation, would need specific JPA query normally
        return pedidoJpaRepository.findAll().stream()
                .filter(p -> p.getStatus() == Pedido.Status.NOVO)
                .toList();
    }

    @Override
    public List<Pedido> recuperarProntosParaEntrega() {
        return pedidoJpaRepository.findAll().stream()
                .filter(p -> p.getStatus() == Pedido.Status.PRONTO)
                .toList();
    }

    @Override
    public Pedido recuperarPorId(long id) {
        return pedidoJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Pedido atualizar(Pedido pedido) {
        return pedidoJpaRepository.save(pedido);
    }

    @Override
    public List<Pedido> recuperarEntreguesEntreDatas(LocalDateTime inicio, LocalDateTime fim) {
        return pedidoJpaRepository.findAll().stream()
                .filter(p -> p.getStatus() == Pedido.Status.ENTREGUE &&
                        p.getDataHoraPagamento() != null &&
                        p.getDataHoraPagamento().isAfter(inicio) && p.getDataHoraPagamento().isBefore(fim))
                .toList();
    }

    @Override
    public List<Pedido> recuperarPorClienteEEntreguesEntreDatas(String cpf, LocalDateTime inicio, LocalDateTime fim) {
        return pedidoJpaRepository.findAll().stream()
                .filter(p -> p.getCliente().getCpf().equals(cpf) &&
                        p.getStatus() == Pedido.Status.ENTREGUE &&
                        p.getDataHoraPagamento() != null &&
                        p.getDataHoraPagamento().isAfter(inicio) && p.getDataHoraPagamento().isBefore(fim))
                .toList();
    }
}
