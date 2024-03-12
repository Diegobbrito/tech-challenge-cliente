package br.com.fiap.cliente.config;

import br.com.fiap.cliente.api.adapter.CustomMessageAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new CustomMessageAdapter(objectMapper);
    }
}

