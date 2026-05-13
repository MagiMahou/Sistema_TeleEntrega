package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Service
public class EstoqueServiceFake implements IEstoqueService {
    @Override
    public boolean verificarDisponibilidade(Pedido pedido) {
        return true; 
    }
}