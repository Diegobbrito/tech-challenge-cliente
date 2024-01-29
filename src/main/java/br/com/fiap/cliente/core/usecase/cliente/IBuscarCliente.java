package br.com.fiap.cliente.core.usecase.cliente;

import br.com.fiap.cliente.api.dto.response.ClienteResponse;

import java.util.List;

public interface IBuscarCliente {
    List<ClienteResponse> buscarTodos();
    ClienteResponse buscarClientePorCpf(String cpf);
}
