package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.CadastrarClienteRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

@Component
public class CadastrarClienteUC {
    private final IClientesRepository clientesRepository;

    public CadastrarClienteUC(IClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public void run(CadastrarClienteRequest request) {
        if (clientesRepository.recuperarPorEmail(request.email()) != null) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        if (clientesRepository.recuperarPorCpf(request.cpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        
        Cliente cliente = new Cliente(request.cpf(), request.nome(), request.email(), request.endereco(), request.senha());
        clientesRepository.salvar(cliente);
    }
}