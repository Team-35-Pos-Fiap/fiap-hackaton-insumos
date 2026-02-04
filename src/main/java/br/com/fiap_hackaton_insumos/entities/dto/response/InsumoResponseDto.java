package br.com.fiap_hackaton_insumos.entities.dto.response;


import java.time.LocalDate;
import java.util.UUID;

public record InsumoResponseDto(UUID id,
                                String nome,
                                String descricao,
                                String fabricante,
                                String cnpjFabricante,
                                LocalDate dataFabricacao,
                                LocalDate dataVencimento,
                                String lote,
                                Boolean vencido,
                                Long diasParaVencer) { }