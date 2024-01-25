package br.com.fiap.cliente.api.handler;


import br.com.fiap.cliente.core.exception.ClienteCadastradoException;
import br.com.fiap.cliente.core.exception.ClienteInexistenteException;
import br.com.fiap.cliente.core.exception.CpfInvalidoException;
import br.com.fiap.cliente.core.exception.EmailInvalidoException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlerClienteInexistenteException(ClienteInexistenteException ex){
        final var details = new ExceptionDetails(ex.getMessage());
        return new ResponseEntity(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlerCpfInvalidoException(CpfInvalidoException ex){
        final var details = new ExceptionDetails(ex.getMessage());
        return ResponseEntity.badRequest().body(details);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlerEmailInvalidoException(EmailInvalidoException ex){
        final var details = new ExceptionDetails(ex.getMessage());
        return ResponseEntity.badRequest().body(details);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlerClienteCadastradoException(ClienteCadastradoException ex){
        final var details = new ExceptionDetails(ex.getMessage());
        return ResponseEntity.badRequest().body(details);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDetails> handlerException(Exception ex){
        final var details = new ExceptionDetails(ex.getMessage());
        return new ResponseEntity(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@Getter
class ExceptionDetails {
    private String error;
    private LocalDateTime timestamp;

    public ExceptionDetails( String details) {
        this.error = details;
        this.timestamp = LocalDateTime.now();
    }
}
