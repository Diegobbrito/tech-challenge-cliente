package br.com.fiap.cliente.core.usecase.cliente;

import br.com.fiap.cliente.api.dto.request.ClienteRequest;
import br.com.fiap.cliente.api.dto.response.ClienteResponse;

public interface ICriarCliente {
    ClienteResponse criar(ClienteRequest produto);
}
