package br.com.fiap.cliente.core.usecase.cliente;

import br.com.fiap.cliente.config.UseCase;
import br.com.fiap.cliente.core.utils.CpfFormatadorUtil;
import br.com.fiap.cliente.gateway.repository.IClienteRepository;

@UseCase
public class GerenciarClienteUseCase implements IGerenciarCliente {

    private final IClienteRepository repository;

    public GerenciarClienteUseCase(IClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void desabilitar(String cpf) {
        final var cpfFormatado = CpfFormatadorUtil.formatarCpf(cpf);
        final var cliente = this.repository.buscarClientePorCpf(cpfFormatado);
        cliente.setActive(Boolean.FALSE);
        repository.atualizar(cliente);
    }
}
