package br.com.fiap_hackaton_insumos.controllers;

import br.com.fiap_hackaton_insumos.entities.dto.request.InsumoRequestDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.InsumoPaginacaoResponseDto;
import br.com.fiap_hackaton_insumos.entities.dto.response.InsumoResponseDto;
import br.com.fiap_hackaton_insumos.mappers.InsumoMapper;
import br.com.fiap_hackaton_insumos.services.interfaces.IInsumoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Slf4j
public class InsumoController {

    private final IInsumoService insumoService;

    public InsumoController(IInsumoService insumoService) {
        this.insumoService = insumoService;
    }

    @PostMapping
    public ResponseEntity<InsumoResponseDto> cadastraInsumo(@Valid @RequestBody @NotNull InsumoRequestDto insumoRequestDto) {
        log.info("Recebendo requisição para criar insumo: {}", insumoRequestDto.lote());

        InsumoResponseDto response = insumoService.criarInsumo(insumoRequestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<InsumoPaginacaoResponseDto> buscarInsumos(@RequestParam(defaultValue = "1") final Integer pagina){
        log.info("Listando todos os insumos");

        return ResponseEntity.ok().body(insumoService.listarTodos(pagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoResponseDto> buscarInsumoPorId(@PathVariable @NotNull @Valid UUID id) {
        log.debug("Buscando insumo por ID: {}", id);

        return ResponseEntity.ok().body(insumoService.buscarPorId(id));
    }

    @GetMapping("/lote/{lote}")
    public ResponseEntity<InsumoResponseDto> buscarPorLote(@PathVariable @NotNull @Valid String lote){
        log.debug("Buscando insumo por lote: {}", lote);

        return ResponseEntity.ok().body(insumoService.buscarPorLote(lote));
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<InsumoResponseDto>> listarVencidos() {
        log.debug("Listando insumos vencidos");

        List<InsumoResponseDto> response = insumoService.listarVencidos()
                .stream()
                .map(InsumoMapper::toInsumoResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/vencendo/{dias}")
    public ResponseEntity<List<InsumoResponseDto>> listarVencendoEm(@PathVariable @NotNull @Valid Integer dias){
        log.debug("Listando insumos vencendo em {} dias", dias);

        List<InsumoResponseDto> response = insumoService.listarVencendoEm(dias)
                .stream()
                .map(InsumoMapper::toInsumoResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fabricante/{fabricante}")
    public ResponseEntity<List<InsumoResponseDto>> listarPorFabricante(@PathVariable @NotNull @Valid String fabricante){
        log.debug("Listando insumos do fabricante: {}", fabricante);

        List<InsumoResponseDto> response = insumoService.listarPorFabricante(fabricante)
                .stream()
                .map(InsumoMapper::toInsumoResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<InsumoResponseDto>> buscarPorNome(@RequestParam @NotNull @Valid String nome){
        log.debug("Buscando insumos com nome contendo: {}", nome);

        List<InsumoResponseDto> response = insumoService.buscarPorNome(nome)
                .stream()
                .map(InsumoMapper::toInsumoResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponseDto> atualizarInsumo(
            @PathVariable @NotNull @Valid UUID id,
            @Valid @RequestBody InsumoRequestDto insumoRequestDto) {

        log.info("Atualizando insumo com ID: {}", id);

        InsumoResponseDto response = insumoService.atualizarInsumo(id, insumoRequestDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInsumo(@PathVariable @NotNull @Valid UUID id) {
        log.info("Deletando insumo com ID: {}", id);

        insumoService.deletarInsumo(id);

        return ResponseEntity.noContent().build();
    }
}