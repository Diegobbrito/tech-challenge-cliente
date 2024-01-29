package br.com.fiap.cliente.api.controllers;

import br.com.fiap.cliente.api.dto.request.ClienteRequest;

import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import br.com.fiap.cliente.core.usecase.cliente.IBuscarCliente;
import br.com.fiap.cliente.core.usecase.cliente.ICriarCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Clientes", description = "Controle de clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final IBuscarCliente buscarClienteUseCase;
    private final ICriarCliente criarClienteUseCase;
    public ClienteController(IBuscarCliente buscarClienteUseCase, ICriarCliente criarClienteUseCase) {
        this.buscarClienteUseCase = buscarClienteUseCase;
        this.criarClienteUseCase = criarClienteUseCase;
    }

    @Operation(summary = "Busca todos os clientes")
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> buscaClientes(){
        return ResponseEntity.ok(buscarClienteUseCase.buscarTodos());
    }

    @Operation(summary = "Busca de cliente por cpf")
    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteResponse> buscaClientePorCpf(@Parameter(example = "055.069.020-42") @PathVariable String cpf){
        return ResponseEntity.ok(buscarClienteUseCase.buscarClientePorCpf(cpf));
    }

    @Operation(summary = "Criação de cliente")
    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody ClienteRequest request){
        final var response = criarClienteUseCase.criar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
