package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class ListarPedidosClienteUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;

    public ListarPedidosClienteUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
    }

    public List<PedidoResponse> run(String cpf, String token) {
        if (!authService.possuiPerfil(token, Perfil.CLIENTE)) {
            throw new SecurityException("Acesso negado: Apenas clientes podem consultar seus pedidos.");
        }
        
        return pedidosRepository.recuperarPorCliente(cpf).stream()
                .map(p -> new PedidoResponse(p.getId(), p.getStatus().name(), p.getValor(), p.getDesconto(), p.getImpostos(), p.getValorCobrado(), ""))
                .toList();
    }
}
