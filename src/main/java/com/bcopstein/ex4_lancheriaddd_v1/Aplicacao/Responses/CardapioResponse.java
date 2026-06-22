package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import java.util.List;

public record CardapioResponse(long id, String titulo, List<Produto> produtos) {
}
