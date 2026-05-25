package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import java.util.List;

public interface IPedidosRepository {
    Pedido salvar(Pedido pedido);
    List<Pedido> recuperarPendentes();
    List<Pedido> recuperarProntosParaEntrega();
    Pedido recuperarPorId(long id);
    Pedido atualizar(Pedido pedido);
}  