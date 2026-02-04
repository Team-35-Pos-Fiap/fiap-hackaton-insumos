package br.com.fiap_hackaton_insumos.services;

import br.com.fiap_hackaton_insumos.entities.db.InsumoDb;
import br.com.fiap_hackaton_insumos.entities.domain.Insumo;
import br.com.fiap_hackaton_insumos.entities.dto.request.InsumoRequestDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.InsumoPaginacaoResponseDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.InsumoResponseDto;
import br.com.fiap_hackaton_insumos.mappers.InsumoMapper;
import br.com.fiap_hackaton_insumos.repositories.interfaces.IInsumoRepository;
import br.com.fiap_hackaton_insumos.services.exceptions.CnpjInvalidoException;
import br.com.fiap_hackaton_insumos.services.exceptions.DataInvalidaException;
import br.com.fiap_hackaton_insumos.services.exceptions.LoteDuplicadoException;
import br.com.fiap_hackaton_insumos.services.interfaces.IInsumoService;
import br.com.fiap_hackaton_insumos.utils.MensagensUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InsumoService implements IInsumoService {

    private final IInsumoRepository insumoRepository;

    public InsumoService(IInsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    @Override
    public InsumoResponseDto criarInsumo(InsumoRequestDto insumo) {
        validarInsumo(insumo);

        if (existeInsumoPorLote(insumo.lote())) {
            throw new LoteDuplicadoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_INSUMO_DUPLICADO));
        }

        Insumo insumoDomain = InsumoMapper.toInsumo(insumo);
        InsumoDb insumoDb = InsumoMapper.toInsumoDb(insumoDomain);

        salvar(insumoDb);

        return InsumoMapper.toInsumoResponseDto(insumoDomain);
    }

    @Override
    public InsumoResponseDto buscarPorId(UUID id) {
        if (id == null || !(id instanceof UUID)) {
            throw new IllegalArgumentException("ID inválido");
        }

        InsumoDb insumoDb = insumoRepository.recuperaInsumoPorId(id);
        Insumo insumoDomain = InsumoMapper.toInsumo(insumoDb);

        return InsumoMapper.toInsumoResponseDto(insumoDomain);
    }

    @Override
    public InsumoResponseDto buscarPorLote(String lote) {
        if (lote == null || lote.trim().isEmpty()) {
            throw new IllegalArgumentException("Lote não pode estar vazio");
        }

        InsumoDb insumoDb = insumoRepository.recuperaInsumoPorLote(lote);
        Insumo insumoDomain = InsumoMapper.toInsumo(insumoDb);

        return InsumoMapper.toInsumoResponseDto(insumoDomain);
    }

    @Override
    public InsumoPaginacaoResponseDto listarTodos(final Integer pagina) {
        return InsumoMapper.toInsumoPaginacaoResponseDto(insumoRepository.recuperaDadosInsumos(pagina));
    }

    @Override
    public List<Insumo> listarVencidos() {
        return insumoRepository.recuperaInsumosVencidos()
                .stream()
                .map(InsumoMapper::toInsumo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Insumo> listarVencendoEm(Integer dias) {
        if (dias == null || dias <= 0) {
            throw new IllegalArgumentException("Número de dias deve ser maior que zero");
        }

        LocalDate dataFim = LocalDate.now().plusDays(dias);

        return insumoRepository.recuperaInsumosVencendoEmData(dataFim)
                .stream()
                .map(InsumoMapper::toInsumo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Insumo> listarPorFabricante(String fabricante) {
        if (fabricante == null || fabricante.trim().isEmpty()) {
            throw new IllegalArgumentException("Fabricante não pode estar vazio");
        }

        return insumoRepository.recuperaInsumosPorFabricante(fabricante)
                .stream()
                .map(InsumoMapper::toInsumo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Insumo> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode estar vazio");
        }

        return insumoRepository.recuperaInsumoPorNome(nome)
                .stream()
                .map(InsumoMapper::toInsumo)
                .collect(Collectors.toList());
    }

    @Override
    public InsumoResponseDto atualizarInsumo(UUID id, InsumoRequestDto insumo) {
        if (id == null || !(id instanceof UUID)) {
            throw new IllegalArgumentException("ID inválido");
        }

        InsumoDb insumoDb = insumoRepository.recuperaInsumoPorId(id);

        validarInsumo(insumo);

        if (!insumoDb.getLote().equals(insumo.lote()) && existeInsumoPorLote(insumo.lote())) {
            throw new LoteDuplicadoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_INSUMO_DUPLICADO));
        }

        insumoDb.setNome(insumo.nome());
        insumoDb.setDescricao(insumo.descricao());
        insumoDb.setFabricante(insumo.fabricante());
        insumoDb.setLote(insumo.lote());
        insumoDb.setCnpjFabricante(insumo.cnpjFabricante());
        insumoDb.setDataFabricacao(insumo.dataFabricacao());
        insumoDb.setDataVencimento(insumo.dataVencimento());
        insumoDb.setLote(insumo.lote());

        Insumo insumoDomain = InsumoMapper.toInsumo(insumoDb);

        return InsumoMapper.toInsumoResponseDto(insumoDomain);
    }

    @Override
    public void deletarInsumo(UUID id) {
        if (id == null || !(id instanceof UUID)) {
            throw new IllegalArgumentException("ID inválido");
        }

        insumoRepository.deletarDadosInsumoPorId(id);
    }

    @Override
    public boolean existeInsumoPorLote(String lote) {
        if (lote == null || lote.trim().isEmpty()) {
            return false;
        }
        return insumoRepository.existeInsumoPorLote(lote);
    }

    public InsumoDb salvar(InsumoDb insumo) {
        return insumoRepository.salvarInsumo(insumo);
    }

    private void validarCNPJ(String cnpj) {
        if (!cnpj.matches("^\\d{14}$")) {
            throw new CnpjInvalidoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_CNPJ_INVALIDO));
        }
    }

    private void validarInsumo(InsumoRequestDto insumo) {
        if (insumo == null) {
            throw new IllegalArgumentException("Insumo não pode ser nulo");
        }

        if (insumo.nome() == null || insumo.nome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do insumo é obrigatório");
        }

        if (insumo.dataVencimento() == null) {
            throw new IllegalArgumentException("Data de vencimento é obrigatória");
        }

        if (insumo.dataVencimento().isBefore(LocalDate.now())) {
            throw new DataInvalidaException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_DATA_INVALIDA));
        }

        if (insumo.lote() == null || insumo.lote().trim().isEmpty()) {
            throw new IllegalArgumentException("Lote é obrigatório");
        }

        if (insumo.cnpjFabricante() != null && !insumo.cnpjFabricante().trim().isEmpty()) {
            validarCNPJ(insumo.cnpjFabricante());
        }
    }
}