package br.com.fiap_hackaton_insumos.entities.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "insumos")
@Getter
@Setter
public class InsumoDb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String fabricante;

    @Column(length = 14, unique = true)
    private String cnpjFabricante;

    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false, length = 100, unique = true)
    private String lote;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    public boolean isVencido() {
        return LocalDate.now().isAfter(this.dataVencimento);
    }

    public boolean venceEm(int dias) {
        LocalDate dataAlvo = LocalDate.now().plusDays(dias);
        return !this.dataVencimento.isAfter(dataAlvo) && !this.isVencido();
    }

    public void atualizarNome(String nome) {
        this.nome = nome;
        this.dataAtualizacao = getDataAtual();
    }

    public void atualizarFabricante(String fabricante) {
        this.fabricante = fabricante;
        this.dataAtualizacao = getDataAtual();
    }

    public void atualizarDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        this.dataAtualizacao = getDataAtual();
    }

    public void atualizarDescricao(String descricao) {
        this.descricao = descricao;
        this.dataAtualizacao = getDataAtual();
    }

    private LocalDateTime getDataAtual() {
        return LocalDateTime.now();
    }
}