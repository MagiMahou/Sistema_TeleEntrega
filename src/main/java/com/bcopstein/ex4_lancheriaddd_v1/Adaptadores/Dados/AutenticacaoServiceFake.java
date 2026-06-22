package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;


import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Service
public class AutenticacaoServiceFake implements IAutenticacaoService {
    
    @Override
    public String gerarToken(String identificador, Perfil perfil) {
        throw new UnsupportedOperationException("Geração de token movida para o API Gateway");
    }

    @Override
    public boolean isAutenticado(String token) {
        return token != null && token.startsWith("token-");
    }

    @Override
    public boolean possuiPerfil(String token, Perfil perfil) {
        if (!isAutenticado(token)) return false;
        String[] parts = token.split("-");
        if (parts.length < 4) return false;
        return parts[1].equalsIgnoreCase(perfil.name());
    }

    @Override
    public String extrairCpf(String token) {
        if (!isAutenticado(token)) return null;
        String[] parts = token.split("-");
        if (parts.length < 4) return null;
        return parts[2];
    }
}