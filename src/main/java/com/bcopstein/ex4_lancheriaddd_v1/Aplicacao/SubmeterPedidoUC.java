package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.SubmeterPedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IDescontoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IImpostoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IEstoqueService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubmeterPedidoUC {
    private final ProdutosRepository produtosRepository;
    private final IAutenticacaoService authService;
    private final IEstoqueService estoqueService;
    private final IDescontoService descontoService;
    private final IImpostoService impostoService;

    @Autowired
    public SubmeterPedidoUC(ProdutosRepository produtosRepository, 
                            IAutenticacaoService authService,
                            IEstoqueService estoqueService,
                            IDescontoService descontoService,
                            IImpostoService impostoService) {
        this.produtosRepository = produtosRepository;
        this.authService = authService;
        this.estoqueService = estoqueService;
        this.descontoService = descontoService;
        this.impostoService = impostoService;
    }

    public PedidoResponse run(SubmeterPedidoRequest request) {
        if (!authService.isAutenticado(request.token())) {
            throw new SecurityException("Cliente não autenticado no sistema.");
        }

        Cliente cliente = new Cliente(request.cpfCliente(), "Gabriel Tavares", "519999999", request.enderecoEntrega(), "gabriel@pucrs.br");

        List<ItemPedido> itens = request.itens().stream().map(req -> {
            Produto prod = produtosRepository.recuperaProdutoPorid(req.produtoId());
            if (prod == null) throw new IllegalArgumentException("Produto inválido: " + req.produtoId());
            return new ItemPedido(prod, req.quantidade());
        }).collect(Collectors.toList());

        long idGerado = System.currentTimeMillis();
        Pedido pedido = new Pedido(idGerado, cliente, LocalDateTime.now(), itens, Pedido.Status.NOVO, 0, 0, 0, 0);

        if (!estoqueService.verificarDisponibilidade(pedido)) {
            throw new IllegalStateException("Pedido rejeitado: Ingredientes insuficientes no estoque.");
        }

        double valorImposto = impostoService.calcularImposto(pedido);
        double valorDesconto = descontoService.calcularDesconto(pedido);
     
        pedido.fecharCustoDoPedido(valorDesconto, valorImposto);
        pedido.aprovar();


        return new PedidoResponse(
            pedido.getId(),
            pedido.getStatus().name(),
            pedido.getValor(),
            pedido.getDesconto(),
            pedido.getImpostos(),
            pedido.getValorCobrado(),
            pedido.getCliente().getEndereco()
        );
    }
}