package br.com.fiap_hackaton_insumos.mappers;

import br.com.fiap_hackaton_insumos.entities.db.InsumoDb;
import br.com.fiap_hackaton_insumos.entities.domain.Insumo;
import br.com.fiap_hackaton_insumos.entities.dto.request.InsumoRequestDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.InsumoPaginacaoResponseDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.InsumoResponseDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.PaginacaoDtoResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

@Component
public abstract class InsumoMapper {

    // dto -> domain -> db

    // 1 - dto -> domain

    public static Insumo toInsumo(InsumoRequestDto insumo) {
        return new Insumo(
                null,
                insumo.nome(),
                insumo.descricao(),
                insumo.fabricante(),
                insumo.cnpjFabricante(),
                insumo.dataFabricacao(),
                insumo.dataVencimento(),
                insumo.lote(),
                LocalDateTime.now(),
                null
        );
    }

    // 2 - domain -> db

    public static InsumoDb toInsumoDb(Insumo insumo) {
        return new InsumoDb(
                insumo.getId(),
                insumo.getNome(),
                insumo.getDescricao(),
                insumo.getFabricante(),
                insumo.getCnpjFabricante(),
                insumo.getDataFabricacao(),
                insumo.getDataVencimento(),
                insumo.getLote(),
                insumo.getDataCriacao(),
                insumo.getDataAtualizacao()
        );
    }

    // db -> domain -> dto

    // 3 - db -> domain

    public static Insumo toInsumo(InsumoDb insumo) {
        return new Insumo(
                insumo.getId(),
                insumo.getNome(),
                insumo.getDescricao(),
                insumo.getFabricante(),
                insumo.getCnpjFabricante(),
                insumo.getDataFabricacao(),
                insumo.getDataVencimento(),
                insumo.getLote(),
                insumo.getDataCriacao(),
                insumo.getDataAtualizacao()
        );
    }

    // 4 - domain -> dto

    public static InsumoResponseDto toInsumoResponseDto(Insumo insumo) {

        long diasParaVencer = ChronoUnit.DAYS.between(LocalDate.now(), insumo.getDataVencimento());

        return new InsumoResponseDto(
                insumo.getId(),
                insumo.getNome(),
                insumo.getDescricao(),
                insumo.getFabricante(),
                insumo.getCnpjFabricante(),
                insumo.getDataFabricacao(),
                insumo.getDataVencimento(),
                insumo.getLote(),
                insumo.isVencido(),
                diasParaVencer
        );
    }

    public static InsumoPaginacaoResponseDto toInsumoPaginacaoResponseDto(Page<InsumoDb> dados) {
        List<InsumoResponseDto> insumos = dados.toList()
                .stream()
                .map(i -> InsumoMapper.toInsumo(i))
                .map(i -> InsumoMapper.toInsumoResponseDto(i))
                .collect(Collectors.toList());

        PaginacaoDtoResponse dadosPaginacao = new PaginacaoDtoResponse(dados.getNumber() + 1, dados.getTotalPages(), Long.valueOf(dados.getTotalElements()).intValue());

        return new InsumoPaginacaoResponseDto(insumos, dadosPaginacao);
    }
}