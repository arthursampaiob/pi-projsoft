package br.edu.insper.pi_projsoft.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CriarProdutoRequest(
        @NotBlank @Size(max = 150) String nome,
        @NotBlank @Size(max = 1000) String descricao,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal preco,
        @NotNull @Positive @DecimalMin(value = "10") Integer quantidade,
        @NotNull boolean deletado) {}