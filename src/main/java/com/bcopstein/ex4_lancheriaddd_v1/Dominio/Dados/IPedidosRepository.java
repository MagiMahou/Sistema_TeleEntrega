package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import java.time.LocalDateTime;
import java.util.List;

public interface IPedidosRepository {
    Pedido salvar(Pedido pedido);
    List<Pedido> recuperarPendentes();
    Pedido recuperarPorId(long id);
    Pedido atualizar(Pedido pedido);
    List<Pedido> recuperarEntreguesEntreDatas(LocalDateTime inicio, LocalDateTime fim);
    List<Pedido> recuperarPorClienteEEntreguesEntreDatas(String cpf, LocalDateTime inicio, LocalDateTime fim);
}