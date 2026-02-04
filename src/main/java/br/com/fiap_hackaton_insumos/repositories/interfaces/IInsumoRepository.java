package br.com.fiap_hackaton_insumos.repositories.interfaces;

import br.com.fiap_hackaton_insumos.entities.db.InsumoDb;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IInsumoRepository {
    InsumoDb salvarInsumo(InsumoDb insumo);
    InsumoDb recuperaInsumoPorId(UUID id);
    InsumoDb recuperaInsumoPorLote(String lote);
    List<InsumoDb> recuperaInsumoPorNome(String nome);
    List<InsumoDb> recuperaInsumosPorFabricante(String fabricante);
    List<InsumoDb> recuperaInsumosVencendoEmData(LocalDate dataFim);
    List<InsumoDb> recuperaInsumosVencidos();
    Page<InsumoDb> recuperaDadosInsumos(final Integer pagina);
    void deletarDadosInsumoPorId(UUID id);
    boolean existeInsumoPorLote(String lote);
}