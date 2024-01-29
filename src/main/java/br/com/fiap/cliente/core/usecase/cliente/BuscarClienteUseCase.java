package br.com.fiap.cliente.core.usecase.cliente;

import br.com.fiap.cliente.api.adapter.ClienteAdapter;
import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import br.com.fiap.cliente.config.UseCase;
import br.com.fiap.cliente.gateway.repository.IClienteRepository;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
public class BuscarClienteUseCase implements IBuscarCliente {

    private final IClienteRepository repository;

    public BuscarClienteUseCase(IClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ClienteResponse> buscarTodos() {
        final var clientes = this.repository.buscarTodos();
        return clientes.stream().map(ClienteAdapter::toResponse).collect(Collectors.toList());
    }

    @Override
    public ClienteResponse buscarClientePorCpf(String cpf) {
        final var cpfFormatado = cpf.trim().replaceAll("\\.", "").replaceAll("-", "");
        final var cliente = this.repository.buscarClientePorCpf(cpfFormatado);

        return ClienteAdapter.toResponse(cliente);
    }
}
