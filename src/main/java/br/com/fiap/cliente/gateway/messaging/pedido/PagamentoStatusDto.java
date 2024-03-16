package br.com.fiap.cliente.gateway.messaging.pedido;

public record PagamentoStatusDto(
        Integer pedidoId,
        String documentoCliente,
        boolean checkPagamento
) {
}
