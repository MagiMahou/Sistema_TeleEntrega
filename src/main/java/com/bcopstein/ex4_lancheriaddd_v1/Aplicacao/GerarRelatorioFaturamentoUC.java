package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.RelatorioFaturamentoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;
import java.util.List;

@Component
public class GerarRelatorioFaturamentoUC {
    private final IPedidosRepository pedidosRepository;
    private final IAutenticacaoService authService;

    public GerarRelatorioFaturamentoUC(IPedidosRepository pedidosRepository, IAutenticacaoService authService) {
        this.pedidosRepository = pedidosRepository;
        this.authService = authService;
    }

    public RelatorioFaturamentoResponse run(String token) {
        if (!authService.possuiPerfil(token, Perfil.ADMINISTRADOR)) {
            throw new SecurityException("Acesso negado: Perfil de Administrador exigido.");
        }
        List<Pedido> pedidos = pedidosRepository.recuperarPendentes();
        
        int quantidade = 0;
        double receita = 0.0;
        double descontos = 0.0;

        for (Pedido p : pedidos) {
            if (p.getStatus() == Pedido.Status.PAGO || p.getStatus() == Pedido.Status.PREPARACAO || p.getStatus() == Pedido.Status.PRONTO) {
                quantidade++;
                receita += p.getValorCobrado();
                descontos += p.getDesconto();
            }
        }

        return new RelatorioFaturamentoResponse(quantidade, receita, descontos);
    }
}