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

    @Override
    public boolean verificarDisponibilidade(Pedido pedido) {
        try {
            // Em um sistema real, seria necessario extrair os ids dos ingredientes baseados no produto.
            // Para simplificar e compilar, usamos um ID fixo ou passamos true.
            return estoqueFeignClient.verificarDisponibilidade(1L);
        } catch (Exception e) {
            System.out.println("Erro ao contactar o serviço de estoque: " + e.getMessage());
            return false; 
        }
    }

    @Override
    public void abaterEstoque(Pedido pedido) {
        // Implementação futura do abatimento via FeignClient
    }
}