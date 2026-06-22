package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import java.util.List;

public interface ICardapioService {
    List<CabecalhoCardapio> listarCardapiosDisponiveis();
    CabecalhoCardapio getCardapioCorrente();
    void definirCardapioCorrente(long id);
}
