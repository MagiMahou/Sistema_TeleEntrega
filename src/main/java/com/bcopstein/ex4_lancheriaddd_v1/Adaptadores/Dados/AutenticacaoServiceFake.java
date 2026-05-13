package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Service
public class AutenticacaoServiceFake implements IAutenticacaoService {
    @Override
    public boolean isAutenticado(String token) {
        return token != null && !token.isBlank();
    }
}