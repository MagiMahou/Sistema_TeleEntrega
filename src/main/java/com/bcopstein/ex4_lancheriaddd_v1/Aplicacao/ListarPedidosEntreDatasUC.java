package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ListarPedidosEntreDatasUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;

    public ListarPedidosEntreDatasUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
    }

    public List<PedidoResponse> run(LocalDateTime inicio, LocalDateTime fim, String token) {
        if (!authService.possuiPerfil(token, Perfil.ADMINISTRADOR)) {
            throw new SecurityException("Acesso negado: Perfil de Administrador exigido.");
        }
        return pedidosRepository.recuperarEntreguesEntreDatas(inicio, fim).stream()
            .map(p -> new PedidoResponse(p.getId(), p.getStatus().name(), p.getValor(), 
                p.getDesconto(), p.getImpostos(), p.getValorCobrado(), p.getCliente().getEndereco()))
            .toList();
    }
}