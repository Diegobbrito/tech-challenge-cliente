package br.com.fiap.cliente.api.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;

public record ClienteRequest(
        @Schema(example = "Diego")
        String nome,
        @Schema(example = "diego@postech.com")
        String email,
        @Schema(example = "055.069.020-42")
        String cpf,
        @Schema(example = "R. Fidêncio Ramos, 308 - Vila Olímpia, São Paulo")
        String endereco,
        @Schema(example = "11999999999")
        String telefone
) {
}
