package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface IPedidosRepository {
    Pedido salvar(Pedido pedido);
    List<Pedido> recuperarPendentes();
    Pedido recuperarPorId(long id);
    Pedido atualizar(Pedido pedido);
}