package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;

public interface IAutenticacaoService {
    String gerarToken(String identificador, Perfil perfil);
    boolean isAutenticado(String token);
    boolean possuiPerfil(String token, Perfil perfil);
    String extrairCpf(String token);
}