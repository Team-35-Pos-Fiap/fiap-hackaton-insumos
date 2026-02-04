package br.com.fiap_hackaton_insumos.controllers.responses;

public abstract class MensagemResponse {

    private String mensagem;

    public MensagemResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
