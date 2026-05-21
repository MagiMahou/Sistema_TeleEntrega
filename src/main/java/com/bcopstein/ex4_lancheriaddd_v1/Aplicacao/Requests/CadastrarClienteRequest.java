package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests;

public record CadastrarClienteRequest(String cpf, String nome, String email, String endereco, String senha) {}