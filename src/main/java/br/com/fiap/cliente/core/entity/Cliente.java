package br.com.fiap.cliente.core.entity;

import br.com.fiap.cliente.core.valueobject.Cpf;
import br.com.fiap.cliente.core.valueobject.Email;

public class Cliente {

    private Integer id;
    private final Cpf cpf;
    private final Email email;
    private final String nome;
    private String endereco;
    private String telefone;

    private Boolean active;

    public Cliente(String cpf, String nome, String email, String endereco, String telefone) {
        this.cpf = new Cpf(cpf);
        this.email = new Email(email);
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
