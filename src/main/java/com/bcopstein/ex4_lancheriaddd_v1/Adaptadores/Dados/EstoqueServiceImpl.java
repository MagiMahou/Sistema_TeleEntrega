package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Integracao.EstoqueFeignClient;
import org.springframework.stereotype.Service;

@Service
public class EstoqueServiceImpl implements IEstoqueService {

    private final EstoqueFeignClient estoqueFeignClient;

    public EstoqueServiceImpl(EstoqueFeignClient estoqueFeignClient) {
        this.estoqueFeignClient = estoqueFeignClient;
    }

    private com.bcopstein.ex4_lancheriaddd_v1.DTOs.BaixaEstoqueRequest montarRequisicao(Pedido pedido) {
        java.util.List<com.bcopstein.ex4_lancheriaddd_v1.DTOs.ItemBaixaDTO> itensParaBaixar = new java.util.ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            itensParaBaixar.add(new com.bcopstein.ex4_lancheriaddd_v1.DTOs.ItemBaixaDTO(item.getProduto().getId(), item.getQuantidade()));
        }
        return new com.bcopstein.ex4_lancheriaddd_v1.DTOs.BaixaEstoqueRequest(itensParaBaixar);
    }

    @Override
    public boolean verificarDisponibilidade(Pedido pedido) {
        try {
            return estoqueFeignClient.verificarDisponibilidade(montarRequisicao(pedido));
        } catch (Exception e) {
            System.out.println("Erro ao contactar o serviço de estoque: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void abaterEstoque(Pedido pedido) {
        try {
            estoqueFeignClient.darBaixa(montarRequisicao(pedido));
        } catch (Exception e) {
            System.out.println("Erro ao contactar o serviço de estoque para baixa: " + e.getMessage());
        }
    }
}