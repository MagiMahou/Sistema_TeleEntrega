package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

public class Cliente {
    private String cpf;
    private String nome;
    private String email;
    private String endereco;
    private String senha;

    public Cliente(String cpf, String nome, String email, String endereco, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }
    public String getSenha() { return senha; }
}