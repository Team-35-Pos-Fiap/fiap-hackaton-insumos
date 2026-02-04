package br.com.fiap_hackaton_insumos.entities.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Insumo {
    private UUID id;
    private String nome;
    private String descricao;
    private String fabricante;
    private String cnpjFabricante;
    private LocalDate dataFabricacao;
    private LocalDate dataVencimento;
    private String lote;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public boolean isVencido() {
        return LocalDate.now().isAfter(this.dataVencimento);
    }

    public boolean venceEm(int dias) {
        LocalDate dataAlvo = LocalDate.now().plusDays(dias);
        return !this.dataVencimento.isAfter(dataAlvo) && !this.isVencido();
    }

    public long diasParaVencer() {
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), this.dataVencimento);
    }
}