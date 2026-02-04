package br.com.fiap_hackaton_insumos.services.exceptions;

public class CnpjInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CnpjInvalidoException(String mensagem) {
        super(mensagem);
    }
}