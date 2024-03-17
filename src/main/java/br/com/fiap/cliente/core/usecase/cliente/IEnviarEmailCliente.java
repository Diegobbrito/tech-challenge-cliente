package br.com.fiap.cliente.core.usecase.cliente;

import br.com.fiap.cliente.gateway.messaging.pedido.PagamentoStatusDto;

public interface IEnviarEmailCliente {
    void enviarEmail(PagamentoStatusDto produto);
}
