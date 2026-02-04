package br.com.fiap_hackaton_insumos.repositories.impl;

import br.com.fiap_hackaton_insumos.entities.db.InsumoDb;
import br.com.fiap_hackaton_insumos.repositories.exceptions.InsumoNaoEncontradoException;
import br.com.fiap_hackaton_insumos.repositories.interfaces.IInsumoRepository;
import br.com.fiap_hackaton_insumos.repositories.interfaces.jpa.IInsumoJpaRepository;
import br.com.fiap_hackaton_insumos.services.exceptions.PaginaInvalidaException;
import br.com.fiap_hackaton_insumos.utils.MensagensUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InsumoRepository implements IInsumoRepository {

    private final IInsumoJpaRepository insumoRepository;
    private final Integer QUANTIDADE_REGISTROS = 5;

    public InsumoRepository(IInsumoJpaRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    @Override
    public InsumoDb salvarInsumo(InsumoDb insumo) {
        return insumoRepository.save(insumo);
    }

    @Override
    public InsumoDb recuperaInsumoPorId(UUID id) {
        return getInsumoDb(insumoRepository.findById(id));
    }

    @Override
    public InsumoDb recuperaInsumoPorLote(String lote) {
        return getInsumoDb(insumoRepository.findByLote(lote));
    }

    @Override
    public List<InsumoDb> recuperaInsumoPorNome(String nome) {
        return getListaInsumos(insumoRepository.findByNomeContaining(nome));
    }

    @Override
    public List<InsumoDb> recuperaInsumosPorFabricante(String fabricante) {
        return getListaInsumos(insumoRepository.findByFabricanteIgnoreCase(fabricante));
    }

    @Override
    public List<InsumoDb> recuperaInsumosVencendoEmData(LocalDate dataFim) {
        return getListaInsumos(insumoRepository.findVencendoEm(dataFim));
    }

    @Override
    public List<InsumoDb> recuperaInsumosVencidos() {
        return getListaInsumos(insumoRepository.findAllVencidos());
    }

    @Override
    public Page<InsumoDb> recuperaDadosInsumos(final Integer pagina) {
        if (pagina == null || pagina < 1) {
            throw new PaginaInvalidaException();
        }

        Page<InsumoDb> insumos = insumoRepository.findAll(PageRequest.of(pagina - 1, QUANTIDADE_REGISTROS));

        if(insumos.toList().isEmpty()){
            throw new InsumoNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_INSUMOS_NAO_ENCONTRADOS));
        } else {
            return insumos;
        }
    }

    @Override
    public void deletarDadosInsumoPorId(UUID id) {
        InsumoDb insumo = recuperaInsumoPorId(id);

        insumoRepository.deleteById(insumo.getId());
    }

    @Override
    public boolean existeInsumoPorLote(String lote) {
        return insumoRepository.findByLote(lote).isPresent();
    }

    private InsumoDb getInsumoDb(Optional<InsumoDb> dadosInsumo) {
        if (dadosInsumo.isPresent()) {
            return dadosInsumo.get();
        } else {
            throw new InsumoNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_INSUMO_NAO_ENCONTRADO));
        }
    }

    private List<InsumoDb> getListaInsumos(List<InsumoDb> insumos) {
        if(!insumos.isEmpty()){
            return insumos;
        } else {
            throw new InsumoNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_INSUMOS_NAO_ENCONTRADOS));
        }
    }
}