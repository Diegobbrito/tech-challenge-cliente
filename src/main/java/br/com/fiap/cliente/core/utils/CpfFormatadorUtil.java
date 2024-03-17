package br.com.fiap.cliente.core.utils;

public class CpfFormatadorUtil {
    public static String formatarCpf(String cpf){
        return  cpf.trim().replaceAll("\\.", "").replaceAll("-", "");
    }
}
