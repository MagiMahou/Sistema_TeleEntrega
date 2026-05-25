package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IImpostoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;

@Service
public class ImpostoServiceFixo implements IImpostoService {
    @Override
    public double calcularImposto(Pedido pedido) {
        double subtotal = 0.0;
        
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                subtotal += (item.getProduto().getPreco() * item.getQuantidade());
            }
        }
        return subtotal * 0.10;
    }
}