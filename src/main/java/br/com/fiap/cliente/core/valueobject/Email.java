package br.com.fiap.cliente.core.valueobject;

import br.com.fiap.cliente.core.exception.EmailInvalidoException;

import java.util.regex.Pattern;

public class Email {
    private final String valor;

    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);


    public Email(String email) {
        if(isEmailInvalido(email)){
            throw new EmailInvalidoException("Email inválido.");
        }
        this.valor = email;
    }

    public static boolean isEmailInvalido(String email){
        return !pattern.matcher(email).matches();
    }

    public String getValor() {
        return valor;
    }
}
