package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.LoginRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.LoginResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Perfil;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.IAutenticacaoService;

@Component
public class AutenticarClienteUC {
    private final IClientesRepository clientesRepository;
    private final IAutenticacaoService authService;

    public AutenticarClienteUC(IClientesRepository clientesRepository, IAutenticacaoService authService) {
        this.clientesRepository = clientesRepository;
        this.authService = authService;
    }

    public LoginResponse run(LoginRequest request) {
        Cliente cliente = clientesRepository.recuperarPorEmail(request.email());
        if (cliente == null || !cliente.getSenha().equals(request.senha())) {
            throw new SecurityException("Credenciais inválidas.");
        }
        
        String tokenGerado = authService.gerarToken(cliente.getCpf(), Perfil.CLIENTE);
        return new LoginResponse(cliente.getEmail(), tokenGerado);
    }
}