package br.com.fiap.cliente.core.usecase.cliente;

import br.com.fiap.cliente.api.dto.request.ClienteRequest;
import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import br.com.fiap.cliente.core.entity.Cliente;
import br.com.fiap.cliente.core.exception.ClienteCadastradoException;
import br.com.fiap.cliente.core.exception.CpfInvalidoException;
import br.com.fiap.cliente.core.exception.EmailInvalidoException;
import br.com.fiap.cliente.gateway.repository.cliente.ClienteEntity;
import br.com.fiap.cliente.gateway.repository.cliente.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


class CriarClienteUseCaseTest {


    private CriarClienteUseCase useCase;

    @Mock
    private ClienteRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new CriarClienteUseCase(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarNovoCliente() {
        // Arrange
        var clienteRequest = new ClienteRequest("Diego","diego.teste@teste.com","15212027020");
        var clienteDomain = new Cliente("15212027020","Diego","diego.teste@teste.com");

        when(repository.salvar(any(Cliente.class))).thenReturn(clienteDomain);
        // Act
        var cliente = useCase.criar(clienteRequest);
        // Assert
        verify(repository, times(1)).salvar(any(Cliente.class));

        assertThat(cliente)
                .extracting(ClienteResponse::cpf)
                .isEqualTo("15212027020");
        assertThat(cliente)
                .extracting(ClienteResponse::nome)
                .isEqualTo("Diego");
        assertThat(cliente)
                .extracting(ClienteResponse::email)
                .isEqualTo("diego.teste@teste.com");
    }

    @Test
    void deveRetornarExcessaoAoRegistrarClienteComCpfInvalido() {

        assertThatThrownBy(() -> new Cliente("00000000000","Diego","teste@teste.com"))
                .isInstanceOf(CpfInvalidoException.class)
                .hasMessage("Cpf inválido.");

    }

    @Test
    void deveRetornarExcessaoAoRegistrarClienteComEmailInvalido() {

        assertThatThrownBy(() -> new Cliente("15212027020","Diego","teste.com"))
                .isInstanceOf(EmailInvalidoException.class)
                .hasMessage("Email inválido.");

    }

}
