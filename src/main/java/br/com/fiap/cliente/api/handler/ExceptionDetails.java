package br.com.fiap.cliente.api.handler;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionDetails {
    private String error;
    private LocalDateTime timestamp;

    public ExceptionDetails( String details) {
        this.error = details;
        this.timestamp = LocalDateTime.now();
    }
}
