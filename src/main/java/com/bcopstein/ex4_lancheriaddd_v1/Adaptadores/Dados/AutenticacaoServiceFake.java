package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Service
public class AutenticacaoServiceFake implements IAutenticacaoService {
    
    // Agora mapeamos: Token -> CPF
    private final Map<String, String> sessoesAtivas = new HashMap<>();

    @Override
    public String gerarToken(String identificador) {
        String token = "token-" + identificador + "-" + System.currentTimeMillis();
        sessoesAtivas.put(token, identificador); // Salva a relação
        return token;
    }

    @Override
    public boolean isAutenticado(String token) {
        return token != null && sessoesAtivas.containsKey(token);
    }

    @Override
    public String extrairCpf(String token) {
        return sessoesAtivas.get(token); // Retorna o CPF dono deste token
    }
}