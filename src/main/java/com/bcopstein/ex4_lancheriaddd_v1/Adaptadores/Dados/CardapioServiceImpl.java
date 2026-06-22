package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ICardapioService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardapioServiceImpl implements ICardapioService {
    
    private final List<CabecalhoCardapio> cardapiosDisponiveis;
    private CabecalhoCardapio cardapioCorrente;

    public CardapioServiceImpl() {
        this.cardapiosDisponiveis = List.of(
            new CabecalhoCardapio(1L, "Cardápio Principal"),
            new CabecalhoCardapio(2L, "Cardápio de Inverno")
        );
        this.cardapioCorrente = cardapiosDisponiveis.get(0);
    }

    @Override
    public List<CabecalhoCardapio> listarCardapiosDisponiveis() {
        return cardapiosDisponiveis;
    }

    @Override
    public CabecalhoCardapio getCardapioCorrente() {
        return cardapioCorrente;
    }

    @Override
    public void definirCardapioCorrente(long id) {
        this.cardapioCorrente = cardapiosDisponiveis.stream()
            .filter(c -> c.id() == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Cardápio inválido: " + id));
    }
}
