package br.com.fiap.cliente.gateway.messaging.pedido;

import br.com.fiap.cliente.core.usecase.cliente.IEnviarEmailCliente;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoQueue {
    private final IEnviarEmailCliente enviarEmailCliente;

    private final Gson gson;

    public PedidoQueue(IEnviarEmailCliente enviarEmailCliente, Gson gson) {
        this.enviarEmailCliente = enviarEmailCliente;
        this.gson = gson;
    }


    @RabbitListener(queues = {"${queue.cliente.pedido}"})
    public void validarPagamentoCriado(@Payload String message) {
        PagamentoStatusDto pagamentoDto = gson.fromJson(message, PagamentoStatusDto.class);

        enviarEmailCliente.enviarEmail(pagamentoDto);
    }

}