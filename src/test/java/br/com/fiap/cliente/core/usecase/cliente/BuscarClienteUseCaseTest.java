package br.com.fiap.cliente.core.usecase.cliente;

import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import br.com.fiap.cliente.core.entity.Cliente;
import br.com.fiap.cliente.gateway.repository.cliente.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class BuscarClienteUseCaseTest {


    private BuscarClienteUseCase useCase;

    @Mock
    private ClienteRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscarClienteUseCase(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void devePermitirConsultarTodosOsClientes() {
        // Arrange
        var diego = new Cliente("15212027020","Diego","diego.teste@teste.com");
        var gustavo = new Cliente("21572082089","Gustavo","gustavo.teste@teste.com");
        var clientesResponse = List.of(diego, gustavo);
        when(repository.buscarTodos()).thenReturn(clientesResponse);
        // Act
        var clientes = useCase.buscarTodos();
        // Assert
        verify(repository, times(1)).buscarTodos();
        assertThat(clientes)
                .isInstanceOf(List.class)
                .isNotNull();
        assertThat(clientes.get(0))
                .extracting(ClienteResponse::cpf)
                .isEqualTo("15212027020");
        assertThat(clientes.get(0))
                .extracting(ClienteResponse::nome)
                .isEqualTo("Diego");
        assertThat(clientes)
                .asList()
                .size()
                .isEqualTo(2);
    }

    @Test
    void devePermitirConsultarClientePorCpf() {
        // Arrange
        var clienteDomain = new Cliente("15212027020","Diego","diego.teste@teste.com");

        when(repository.buscarClientePorCpf(anyString())).thenReturn(clienteDomain);
        // Act
        var cliente = useCase.buscarClientePorCpf("15212027020");
        // Assert
        verify(repository, times(1)).buscarClientePorCpf(anyString());

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

}
