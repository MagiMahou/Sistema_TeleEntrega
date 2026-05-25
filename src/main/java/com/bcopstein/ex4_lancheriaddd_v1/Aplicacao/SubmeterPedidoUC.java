package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.SubmeterPedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IPedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IDescontoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IImpostoService;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubmeterPedidoUC {
    private final ProdutosRepository produtosRepository;
    private final IEstoqueService estoqueService;
    private final IImpostoService impostoService;
    private final IDescontoService descontoService;
    private final IAutenticacaoService authService;
    private final IPedidosRepository pedidosRepository;
    private final IClientesRepository clientesRepository;

    public SubmeterPedidoUC(ProdutosRepository produtosRepository, IEstoqueService estoqueService,
            IImpostoService impostoService, IDescontoService descontoService, IAutenticacaoService authService,
            IPedidosRepository pedidosRepository, IClientesRepository clientesRepository) {
        this.produtosRepository = produtosRepository;
        this.estoqueService = estoqueService;
        this.impostoService = impostoService;
        this.descontoService = descontoService;
        this.authService = authService;
        this.pedidosRepository = pedidosRepository;
        this.clientesRepository = clientesRepository;
    }

    public PedidoResponse run(SubmeterPedidoRequest request, String token) {
        if (!authService.possuiPerfil(token, Perfil.CLIENTE)) {
            throw new SecurityException("Acesso negado: Perfil inválido.");
        }

        String cpfCliente = authService.extrairCpf(token);
        Cliente cliente = clientesRepository.recuperarPorCpf(cpfCliente);
        
        if (cliente == null) {
            throw new IllegalStateException("Cliente não encontrado na base de dados.");
        }

        List<ItemPedido> itens = new ArrayList<>();
        for (var itemReq : request.itens()) {
            Produto produto = produtosRepository.recuperaProdutoPorid(itemReq.produtoId());
            if (produto == null) {
                throw new IllegalArgumentException("Produto não encontrado");
            }
            itens.add(new ItemPedido(produto, itemReq.quantidade()));
        }

        Pedido pedido = new Pedido(System.currentTimeMillis(), cliente, null, itens, Pedido.Status.NOVO, 0.0, 0.0, 0.0, 0.0);

        if (!estoqueService.verificarDisponibilidade(pedido)) {
            throw new IllegalArgumentException("Estoque insuficiente para os ingredientes da receita.");
        }

        double imposto = impostoService.calcularImposto(pedido);
        double desconto = descontoService.calcularDesconto(pedido);
        pedido.fecharCustoDoPedido(desconto, imposto);
        pedido.aprovar();
        estoqueService.abaterEstoque(pedido);

        pedidosRepository.salvar(pedido);

        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(), pedido.getValor(), desconto, imposto, pedido.getValorCobrado(), cliente.getEndereco());
    }
}