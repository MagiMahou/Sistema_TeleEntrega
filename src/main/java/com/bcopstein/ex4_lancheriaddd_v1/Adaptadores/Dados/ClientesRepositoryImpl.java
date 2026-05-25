package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.JPA.ClienteJpaRepository;

@Repository
@Primary 
public class ClientesRepositoryImpl implements IClientesRepository {

    private final ClienteJpaRepository jpaRepository;
    public ClientesRepositoryImpl(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void salvar(Cliente cliente) {
        jpaRepository.save(cliente); 
    }

    @Override
    public Cliente recuperarPorEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public Cliente recuperarPorCpf(String cpf) {
        return jpaRepository.findById(cpf).orElse(null);
    }
}