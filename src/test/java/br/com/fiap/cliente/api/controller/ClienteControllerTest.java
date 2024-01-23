package br.com.fiap.cliente.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.cliente.api.controllers.ClienteController;
import br.com.fiap.cliente.api.dto.request.ClienteRequest;
import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import br.com.fiap.cliente.api.handler.RestExceptionHandler;
import br.com.fiap.cliente.core.usecase.cliente.ICriarCliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.fiap.cliente.core.usecase.cliente.IBuscarCliente;

import java.util.List;

class ClienteControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IBuscarCliente buscarClienteUseCase;

    @Mock
    private ICriarCliente criarClienteUseCase;
    AutoCloseable openMocks;

    ClienteControllerTest() {
    }

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        ClienteController mensagemController = new ClienteController(buscarClienteUseCase,criarClienteUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(mensagemController)
                .setControllerAdvice(new RestExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirConsultarTodosOsClientes() throws Exception {
            var diego = new ClienteResponse("15212027020","Diego","diego.teste@teste.com");
            var gustavo = new ClienteResponse("21572082089","Gustavo","gustavo.teste@teste.com");
            var clientes = List.of(diego, gustavo);
            when(buscarClienteUseCase.buscarTodos())
                    .thenReturn(clientes);

            mockMvc.perform(get("/clientes")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].cpf").value(clientes.get(0).cpf()))
                    .andExpect(jsonPath("$[0].nome").value(clientes.get(0).nome()))
                    .andExpect(jsonPath("$[0].email").value(clientes.get(0).email()))
                    .andExpect(jsonPath("$[1].cpf").value(clientes.get(1).cpf()))
                    .andExpect(jsonPath("$[1].nome").value(clientes.get(1).nome()))
                    .andExpect(jsonPath("$[1].email").value(clientes.get(1).email()));
            verify(buscarClienteUseCase, times(1)).buscarTodos();
        }

        @Test
        void devePermitirConsultarOsClientesPorCpf() throws Exception {
            var cliente = new ClienteResponse("15212027020","Diego","diego.teste@teste.com");
            when(buscarClienteUseCase.buscarClientePorCpf(anyString()))
                    .thenReturn(cliente);

            mockMvc.perform(get("/clientes/{cpf}", "15212027020")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cpf").value(cliente.cpf()))
                    .andExpect(jsonPath("$.nome").value(cliente.nome()))
                    .andExpect(jsonPath("$.email").value(cliente.email()));
            verify(buscarClienteUseCase, times(1)).buscarClientePorCpf(anyString());
        }
    }

    @Nested
    class CriarCliente {
        @Test
        void devePermitirRegistrarCliente() throws Exception {

            var clienteRequest = new ClienteRequest("15212027020","Diego","diego.teste@teste.com");
            var clienteResponse = new ClienteResponse("15212027020","Diego","diego.teste@teste.com");
            when(criarClienteUseCase.criar(any(ClienteRequest.class)))
                    .thenReturn(clienteResponse);

            mockMvc.perform(post("/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(clienteRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.cpf").value(clienteResponse.cpf()))
                    .andExpect(jsonPath("$.nome").value(clienteResponse.nome()))
                    .andExpect(jsonPath("$.email").value(clienteResponse.email()));
            verify(criarClienteUseCase, times(1))
                    .criar(any(ClienteRequest.class));

        }

    }




    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
