package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.ClienteJpaRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

@Repository
public class ClientesRepositoryImpl implements IClientesRepository {

    private final ClienteJpaRepository clienteJpaRepository;

    public ClientesRepositoryImpl(ClienteJpaRepository clienteJpaRepository) {
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public void salvar(Cliente cliente) {
        clienteJpaRepository.save(cliente);
    }

    @Override
    public Cliente recuperarPorCpf(String cpf) {
        return clienteJpaRepository.findById(cpf).orElse(null);
    }

    @Override
    public Cliente recuperarPorEmail(String email) {
        return clienteJpaRepository.findByEmail(email);
    }
}
