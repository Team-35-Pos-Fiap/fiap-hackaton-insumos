package br.com.fiap_hackaton_insumos.services.interfaces;

import br.com.fiap_hackaton_insumos.entities.domain.Insumo;
import br.com.fiap_hackaton_insumos.entities.dto.request.InsumoRequestDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.InsumoPaginacaoResponseDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.InsumoResponseDto;

import java.util.List;
import java.util.UUID;

public interface IInsumoService {
    InsumoResponseDto criarInsumo(InsumoRequestDto insumo);
    InsumoResponseDto buscarPorId(UUID id);
    InsumoResponseDto buscarPorLote(String lote);
    InsumoPaginacaoResponseDto listarTodos(final Integer pagina);
    List<Insumo> listarVencidos();
    List<Insumo> listarVencendoEm(Integer dias);
    List<Insumo> listarPorFabricante(String fabricante);
    List<Insumo> buscarPorNome(String nome);
    InsumoResponseDto atualizarInsumo(UUID id, InsumoRequestDto insumo);
    void deletarInsumo(UUID id);
    boolean existeInsumoPorLote(String lote);
}