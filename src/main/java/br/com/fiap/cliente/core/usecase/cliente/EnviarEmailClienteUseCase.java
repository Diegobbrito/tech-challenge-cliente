package br.com.fiap.cliente.core.usecase.cliente;
import br.com.fiap.cliente.config.UseCase;
import br.com.fiap.cliente.gateway.dataprovider.EmailDataProvider;
import br.com.fiap.cliente.gateway.messaging.pedido.PagamentoStatusDto;
import br.com.fiap.cliente.gateway.repository.IClienteRepository;

@UseCase
public class EnviarEmailClienteUseCase implements IEnviarEmailCliente {

    private final IClienteRepository repository;

    private final EmailDataProvider emailDataProvider;

    public EnviarEmailClienteUseCase(IClienteRepository repository, EmailDataProvider emailDataProvider) {
        this.repository = repository;
        this.emailDataProvider = emailDataProvider;
    }

    @Override
    public void enviarEmail(PagamentoStatusDto pagamentoDto) {

        var cliente = repository.buscarClientePorCpf(pagamentoDto.documentoCliente());

        var email = cliente.getEmail().getValor();
        var titulo = "Pedido " + pagamentoDto.pedidoId();
        if(pagamentoDto.checkPagamento())
            emailDataProvider.enviarEmail(email, titulo, "Pagamento realizado com sucesso. Seu pedido está sendo preparado" );
        else
            emailDataProvider.enviarEmail(email, titulo, "Pagamento não realiado." );
    }
}
