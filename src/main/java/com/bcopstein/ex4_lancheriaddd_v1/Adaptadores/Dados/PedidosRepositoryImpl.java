package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.PedidoJpaRepository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Primary
public class PedidosRepositoryImpl implements IPedidosRepository {

    private final PedidoJpaRepository jpaRepository;

    public PedidosRepositoryImpl(PedidoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return jpaRepository.save(pedido);
    }

    @Override
    public List<Pedido> recuperarPendentes() {
        return jpaRepository.findAll().stream()
                .filter(p -> p.getStatus() == Pedido.Status.PAGO || p.getStatus() == Pedido.Status.PREPARACAO)
                .toList();
    }

    @Override
    public List<Pedido> recuperarProntosParaEntrega() {
        return jpaRepository.findAll().stream()
                .filter(p -> p.getStatus() == Pedido.Status.PRONTO)
                .toList();
    }

    @Override
    public Pedido recuperarPorId(long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public Pedido atualizar(Pedido pedido) {
        return jpaRepository.save(pedido);
    }

    @Override
    public List<Pedido> recuperarEntreguesEntreDatas(LocalDateTime inicio, LocalDateTime fim) {
        return jpaRepository.findAll().stream()
                .filter(p -> p.getStatus() == Pedido.Status.ENTREGUE)
                .filter(p -> p.getDataHoraPagamento() != null && !p.getDataHoraPagamento().isBefore(inicio) && !p.getDataHoraPagamento().isAfter(fim))
                .toList();
    }

    @Override
    public List<Pedido> recuperarPorClienteEEntreguesEntreDatas(String cpf, LocalDateTime inicio, LocalDateTime fim) {
        return jpaRepository.findAll().stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getCpf().equals(cpf))
                .filter(p -> p.getStatus() == Pedido.Status.ENTREGUE)
                .filter(p -> p.getDataHoraPagamento() != null && !p.getDataHoraPagamento().isBefore(inicio) && !p.getDataHoraPagamento().isAfter(fim))
                .toList();
    }
}