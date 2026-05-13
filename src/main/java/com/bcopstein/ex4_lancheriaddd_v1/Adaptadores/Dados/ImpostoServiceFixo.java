package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IImpostoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Service
public class ImpostoServiceFixo implements IImpostoService {
    @Override
    public double calcularImposto(Pedido pedido) {
        return pedido.getValor() * 0.10;
    }
}