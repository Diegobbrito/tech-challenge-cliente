package br.com.fiap.cliente.api.adapter;

import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import br.com.fiap.cliente.core.entity.Cliente;
import br.com.fiap.cliente.gateway.repository.cliente.ClienteEntity;

public class ClienteAdapter {

    private ClienteAdapter(){}

    public static ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getCpf().getValor(),
                cliente.getNome(),
                cliente.getEmail().getValor());
    }

    public static Cliente toCliente(ClienteEntity clienteEntity){
        final var cliente = new Cliente(
                clienteEntity.getCpf(),
                clienteEntity.getNome(),
                clienteEntity.getEmail(),
                clienteEntity.getEndereco(),
                clienteEntity.getTelefone());
        cliente.setId(clienteEntity.getId());
        return cliente;
    }
}
