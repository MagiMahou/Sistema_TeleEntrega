package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Service
public class AutenticacaoServiceFake implements IAutenticacaoService {
    
    private static class Sessao {
        String identificador;
        Perfil perfil;

        Sessao(String identificador, Perfil perfil) {
            this.identificador = identificador;
            this.perfil = perfil;
        }
    }

    private final Map<String, Sessao> sessoesAtivas = new HashMap<>();

    @Override
    public String gerarToken(String identificador, Perfil perfil) {
        String token = "token-" + perfil.name().toLowerCase() + "-" + identificador + "-" + System.currentTimeMillis();
        sessoesAtivas.put(token, new Sessao(identificador, perfil));
        return token;
    }

    @Override
    public boolean isAutenticado(String token) {
        return token != null && sessoesAtivas.containsKey(token);
    }

    @Override
    public boolean possuiPerfil(String token, Perfil perfil) {
        return isAutenticado(token) && sessoesAtivas.get(token).perfil == perfil;
    }

    @Override
    public String extrairCpf(String token) {
        if (!isAutenticado(token)) return null;
        return sessoesAtivas.get(token).identificador;
    }
}