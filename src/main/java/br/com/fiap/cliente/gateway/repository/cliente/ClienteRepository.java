package br.com.fiap.cliente.gateway.repository.cliente;

import br.com.fiap.cliente.api.adapter.ClienteAdapter;
import br.com.fiap.cliente.core.entity.Cliente;
import br.com.fiap.cliente.core.exception.ClienteCadastradoException;
import br.com.fiap.cliente.core.exception.ClienteInexistenteException;
import br.com.fiap.cliente.gateway.repository.IClienteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteRepository implements IClienteRepository {

    private final JpaClienteRepository repository;

    public ClienteRepository(JpaClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cliente> buscarTodos() {
        final var clientes = repository.findAllByActiveIsTrue();
        return clientes.stream().map(ClienteAdapter::toCliente).collect(Collectors.toList());
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        final var checkCliente = repository
                .findByCpfAndActiveIsTrue(cliente.getCpf().getValor());
        if(checkCliente.isPresent()){
            throw new ClienteCadastradoException("Cliente já cadastrado.");
        }
        final var clienteEntity = new ClienteEntity(cliente);
        final var entity = repository.save(clienteEntity);
        return ClienteAdapter.toCliente(entity);
    }

    @Override
    public void atualizar(Cliente cliente) {
        final var clienteEntity = new ClienteEntity(cliente);
        repository.save(clienteEntity);
    }




    @Override
    public Cliente buscarClientePorCpf(String cpf) {
        final var cliente = repository
                .findByCpfAndActiveIsTrue(cpf)
                .orElseThrow(() -> new ClienteInexistenteException("Cliente não entrado."));
        return ClienteAdapter.toCliente(cliente);
    }
}
