package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.ValidarLoginRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.ValidarLoginResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

@Component
public class ValidarLoginUC {
    private final IClientesRepository clientesRepository;

    public ValidarLoginUC(IClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public ValidarLoginResponse run(ValidarLoginRequest request) {
        Cliente cliente = clientesRepository.recuperarPorEmail(request.email());
        if (cliente != null && cliente.getSenha().equals(request.senha())) {
            return new ValidarLoginResponse(cliente.getCpf());
        }
        throw new IllegalArgumentException("Credenciais inválidas");
    }
}
