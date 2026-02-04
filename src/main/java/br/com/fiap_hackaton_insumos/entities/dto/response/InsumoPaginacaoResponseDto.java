package br.com.fiap_hackaton_insumos.entities.dto.response;

import java.util.List;

public record InsumoPaginacaoResponseDto(List<InsumoResponseDto> insumos, PaginacaoDtoResponse dadosPaginacao) { }