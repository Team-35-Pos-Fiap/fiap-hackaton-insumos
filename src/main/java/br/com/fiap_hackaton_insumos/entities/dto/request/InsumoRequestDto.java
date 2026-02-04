package br.com.fiap_hackaton_insumos.entities.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record InsumoRequestDto(@NotBlank(message = "Nome é obrigatório")
                               @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
                               String nome,

                               String descricao,

                               String fabricante,

                               @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve conter exatamente 14 dígitos")
                               String cnpjFabricante,

                               LocalDate dataFabricacao,

                               @NotNull(message = "Data de vencimento é obrigatória")
                               @FutureOrPresent(message = "Data de vencimento deve ser presente ou futura")
                               LocalDate dataVencimento,

                               @NotBlank(message = "Lote é obrigatório")
                               @Size(min = 3, max = 100, message = "Lote deve ter entre 3 e 100 caracteres")
                               String lote){ }