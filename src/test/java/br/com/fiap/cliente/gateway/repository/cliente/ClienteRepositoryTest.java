package br.com.fiap.cliente.gateway.repository.cliente;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import br.com.fiap.cliente.core.entity.Cliente;

import br.com.fiap.cliente.core.exception.ClienteCadastradoException;
import br.com.fiap.cliente.core.exception.ClienteInexistenteException;
import br.com.fiap.cliente.core.exception.EmailInvalidoException;
import br.com.fiap.cliente.core.valueobject.Cpf;
import br.com.fiap.cliente.core.valueobject.Email;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class ClienteRepositoryTest {


    private ClienteRepository clienteRepository;

    @Mock
    private JpaClienteRepository jpaRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteRepository = new ClienteRepository(jpaRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class ListarClientes {

        @Test
        void devePermitirConsultarTodosOsClientes() {
            // Arrange
            Cliente cliente = new Cliente(
                    "28655919055",
                    "Diego",
                    "diego.teste@teste.com");
            cliente.setId(1);
            var clientesMock = new ClienteEntity(cliente);
            when(jpaRepository.findAll()).thenReturn(List.of(clientesMock));
            // Act
            var clienteResponse = clienteRepository.buscarTodos();
            // Assert
            verify(jpaRepository, times(1))
                    .findAll();
            assertThat(clienteResponse)
                    .isInstanceOf(List.class)
                    .isNotNull();
            assertThat(clienteResponse.get(0))
                    .extracting(Cliente::getId)
                    .isEqualTo(1);
            assertThat(clienteResponse.get(0))
                    .extracting(Cliente::getNome)
                    .isEqualTo("Diego");
            assertThat(clienteResponse.get(0).getEmail())
                    .extracting(Email::getValor)
                    .isEqualTo("diego.teste@teste.com");
        }

        @Test
        void devePermitirConsultarOsClientesPorCpf() {
            // Arrange
            Cliente cliente = new Cliente(
                    "28655919055",
                    "Diego",
                    "diego.teste@teste.com");
            cliente.setId(1);
            var clientesMock = new ClienteEntity(cliente);
            when(jpaRepository.findByCpf(anyString())).thenReturn(Optional.of(clientesMock));
            // Act
            var clienteResponse = clienteRepository.buscarClientePorCpf("28655919055");
            // Assert
            verify(jpaRepository, times(1))
                    .findByCpf(anyString());
            assertThat(clienteResponse)
                    .isInstanceOf(Cliente.class)
                    .isNotNull();
            assertThat(clienteResponse)
                    .extracting(Cliente::getId)
                    .isEqualTo(1);
            assertThat(clienteResponse)
                    .extracting(Cliente::getNome)
                    .isEqualTo("Diego");
            assertThat(clienteResponse.getEmail())
                    .extracting(Email::getValor)
                    .isEqualTo("diego.teste@teste.com");
        }

        @Test
        void deveRetornarExcessaoAoBuscarClienteInexistente() {
            // Arrange
            when(jpaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
            // Act
            assertThatThrownBy(() -> clienteRepository.buscarClientePorCpf("28655919055"))
                    .isInstanceOf(ClienteInexistenteException.class)
                    .hasMessage("Cliente não entrado.");
            // Assert
            verify(jpaRepository, times(1)).findByCpf(anyString());
        }
    }

    @Nested
    class CriarCliente {
        @Test
        void devePermitirRegistrarCliente() {
            // Arrange
            var cliente = new Cliente("15212027020","Diego","diego.teste@teste.com");
            cliente.setId(1);
            var clienteEntity = new ClienteEntity(cliente);
            when(jpaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
            when(jpaRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);
            // Act
            var clienteSalvo = clienteRepository.salvar(cliente);
            // Assert
            verify(jpaRepository, times(1)).findByCpf(anyString());
            verify(jpaRepository, times(1)).save(any(ClienteEntity.class));
            assertThat(clienteSalvo)
                    .isInstanceOf(Cliente.class)
                    .isNotNull();
            assertThat(clienteSalvo)
                    .extracting(Cliente::getId)
                    .isEqualTo(cliente.getId());
            assertThat(clienteSalvo)
                    .extracting(Cliente::getNome)
                    .isEqualTo(cliente.getNome());
            assertThat(clienteSalvo.getEmail())
                    .extracting(Email::getValor)
                    .isEqualTo(cliente.getEmail().getValor());
            assertThat(clienteSalvo.getCpf())
                    .extracting(Cpf::getValor)
                    .isEqualTo(cliente.getCpf().getValor());
        }

        @Test
        void deveRetornarExcessaoAoRegistrarClienteExistente() {
            // Arrange
            var cliente = new Cliente("15212027020","Diego","diego.teste@teste.com");
            cliente.setId(1);
            var clienteEntity = new ClienteEntity(cliente);
            when(jpaRepository.findByCpf(anyString())).thenReturn(Optional.of(clienteEntity));
            // Act
            assertThatThrownBy(() -> clienteRepository.salvar(cliente))
                    .isInstanceOf(ClienteCadastradoException.class)
                    .hasMessage("Cliente já cadastrado.");
            // Assert
            verify(jpaRepository, times(1)).findByCpf(anyString());
            verify(jpaRepository, times(0)).save(any(ClienteEntity.class));
        }
    }

}
