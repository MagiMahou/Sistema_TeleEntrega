package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IDescontoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Service
public class DescontoServiceFixo implements IDescontoService {
    @Override
    public double calcularDesconto(Pedido pedido) {
        return pedido.getValor() * 0.07;
    }
}