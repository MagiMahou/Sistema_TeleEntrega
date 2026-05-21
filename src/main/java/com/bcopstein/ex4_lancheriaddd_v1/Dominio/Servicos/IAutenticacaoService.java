package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

public interface IAutenticacaoService {
    String gerarToken(String identificador);
    boolean isAutenticado(String token);
    String extrairCpf(String token); // Novo método!
}