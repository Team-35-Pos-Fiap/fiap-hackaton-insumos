package br.com.fiap_hackaton_insumos.repositories.interfaces.jpa;

import br.com.fiap_hackaton_insumos.entities.db.InsumoDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IInsumoJpaRepository extends JpaRepository<InsumoDb, UUID> {
    Optional<InsumoDb> findById(UUID id);

    Optional<InsumoDb> findByLote(String lote);

    List<InsumoDb> findByFabricanteIgnoreCase(String fabricante);

    List<InsumoDb> findByNomeIgnoreCase(String nome);

    @Query("SELECT i FROM InsumoDb i WHERE LOWER(i.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<InsumoDb> findByNomeContaining(@Param("nome") String nome);

    @Query("SELECT i FROM InsumoDb i WHERE i.dataVencimento < CURRENT_DATE")
    List<InsumoDb> findAllVencidos();

    @Query("SELECT i FROM InsumoDb i WHERE i.dataVencimento BETWEEN CURRENT_DATE AND :dataFim")
    List<InsumoDb> findVencendoEm(@Param("dataFim") LocalDate dataFim);
}