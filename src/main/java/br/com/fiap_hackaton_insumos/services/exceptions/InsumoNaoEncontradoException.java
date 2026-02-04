package br.com.fiap_hackaton_insumos.services.exceptions;

public class InsumoNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InsumoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}