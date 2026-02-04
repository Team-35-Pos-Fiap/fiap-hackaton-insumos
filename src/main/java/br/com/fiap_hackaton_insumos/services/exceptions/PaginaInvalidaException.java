package br.com.fiap_hackaton_insumos.services.exceptions;

import br.com.fiap_hackaton_insumos.utils.MensagensUtil;

public class PaginaInvalidaException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public PaginaInvalidaException() {
    super(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PAGINA_INVALIDA));
  }
}