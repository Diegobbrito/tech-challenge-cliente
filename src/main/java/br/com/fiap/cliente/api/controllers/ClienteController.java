package br.com.fiap.cliente.api.controllers;

import br.com.fiap.cliente.api.dto.request.ClienteRequest;

import br.com.fiap.cliente.api.dto.response.ClienteResponse;
import br.com.fiap.cliente.core.usecase.cliente.IBuscarCliente;
import br.com.fiap.cliente.core.usecase.cliente.ICriarCliente;
import br.com.fiap.cliente.core.usecase.cliente.IGerenciarCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Controle de clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final IBuscarCliente buscarClienteUseCase;
    private final ICriarCliente criarClienteUseCase;
    private final IGerenciarCliente gerenciarClienteUseCase;
    public ClienteController(IBuscarCliente buscarClienteUseCase, ICriarCliente criarClienteUseCase, IGerenciarCliente gerenciarClienteUseCase) {
        this.buscarClienteUseCase = buscarClienteUseCase;
        this.criarClienteUseCase = criarClienteUseCase;
        this.gerenciarClienteUseCase = gerenciarClienteUseCase;
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

    @Operation(summary = "Desabilitar cliente por cpf")
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> desabilitarCliente(@Parameter(example = "055.069.020-42") @PathVariable String cpf){
        buscarClienteUseCase.buscarClientePorCpf(cpf);
        gerenciarClienteUseCase.desabilitar(cpf);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Criação de cliente")
    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody ClienteRequest request){
        final var response = criarClienteUseCase.criar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
