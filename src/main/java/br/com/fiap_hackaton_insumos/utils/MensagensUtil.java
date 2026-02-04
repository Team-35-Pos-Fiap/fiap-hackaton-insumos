package br.com.fiap_hackaton_insumos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MensagensUtil {
    private static MessageSource messageSource;

    private static Locale locale = Locale.of("pt", "BR");

    public static final String ERRO_INTERNAL_SERVER_ERROR = "exception.internal_server_error";
    public static final String ERRO_INSUMO_NAO_ENCONTRADO = "exception.insumo_nao_encontrado";
    public static final String ERRO_INSUMOS_NAO_ENCONTRADOS = "exception.insumos_nao_encontrados";
    public static final String ERRO_PARAMETRO_INVALIDO = "exception.erro_parametro_invalido";
    public static final String ERRO_PAGINA_INVALIDA = "exception.pagina_invalida";
    public static final String ERRO_INSUMO_DUPLICADO = "exception.insumo_duplicado";
    public static final String ERRO_DATA_INVALIDA = "exception.data_invalida";
    public static final String ERRO_CNPJ_INVALIDO = "exception.cnpj_invalido";

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MensagensUtil.messageSource = messageSource;
    }

    public static String recuperarMensagem(String mensagem, Object ... parametros) {
        return messageSource.getMessage(mensagem, parametros, locale);
    }
}
