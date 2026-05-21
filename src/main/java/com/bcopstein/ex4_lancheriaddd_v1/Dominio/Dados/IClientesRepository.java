package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

public interface IClientesRepository {
    void salvar(Cliente cliente);
    Cliente recuperarPorEmail(String email);
    Cliente recuperarPorCpf(String cpf);
}