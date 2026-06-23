package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.LoginRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.LoginResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class AutenticarFuncionarioUC {
    private final IAutenticacaoService authService;

    public AutenticarFuncionarioUC(IAutenticacaoService authService) {
        this.authService = authService;
    }

    public LoginResponse run(LoginRequest request) {
        if ("cozinha@lanchonete.com".equals(request.email()) && "cozinha123".equals(request.senha())) {
            String token = authService.gerarToken("cozinha01", Perfil.COZINHA);
            return new LoginResponse(request.email(), token);
        }
        if ("admin@lanchonete.com".equals(request.email()) && "admin123".equals(request.senha())) {
            String token = authService.gerarToken("admin01", Perfil.ADMINISTRADOR);
            return new LoginResponse(request.email(), token);
        }

        throw new SecurityException("Credenciais internas inválidas.");
    }
}