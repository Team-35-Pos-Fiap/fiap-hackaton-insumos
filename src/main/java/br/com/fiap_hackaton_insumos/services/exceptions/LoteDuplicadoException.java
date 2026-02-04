package br.com.fiap_hackaton_insumos.services.exceptions;

public class LoteDuplicadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoteDuplicadoException(String mensagem) {
        super(mensagem);
    }
}