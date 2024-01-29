package br.com.fiap.cliente.core.entity;

import br.com.fiap.cliente.core.valueobject.Cpf;
import br.com.fiap.cliente.core.valueobject.Email;

public class Cliente {

    private Integer id;
    private final Cpf cpf;
    private final Email email;
    private final String nome;

    public Cliente(String cpf, String nome, String email) {
        this.cpf = new Cpf(cpf);
        this.email = new Email(email);
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }
}
