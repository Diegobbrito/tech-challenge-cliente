package br.com.fiap.cliente.gateway.repository;

import br.com.fiap.cliente.core.entity.Cliente;

import java.util.List;

public interface IClienteRepository {
    List<Cliente> buscarTodos();
    Cliente salvar(Cliente produto);

    Cliente buscarClientePorCpf(String cpf);
}
