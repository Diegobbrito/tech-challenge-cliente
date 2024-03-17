package br.com.fiap.cliente.core.usecase.cliente;
import br.com.fiap.cliente.api.adapter.ClienteAdapter;
import br.com.fiap.cliente.api.dto.request.ClienteRequest;
import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import br.com.fiap.cliente.config.UseCase;
import br.com.fiap.cliente.core.entity.Cliente;
import br.com.fiap.cliente.gateway.repository.IClienteRepository;
@UseCase
public class CriarClienteUseCase implements ICriarCliente {

    private final IClienteRepository repository;

    public CriarClienteUseCase(IClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClienteResponse criar(ClienteRequest request) {
        final var cliente = new Cliente(
                request.cpf(),
                request.nome(),
                request.email(),
                request.endereco(),
                request.telefone());
        return ClienteAdapter.toResponse(repository.salvar(cliente));
    }
}
