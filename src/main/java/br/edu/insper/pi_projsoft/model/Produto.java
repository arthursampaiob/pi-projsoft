package br.edu.insper.pi_projsoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String nome;
    @Column(nullable = false, length = 1000)
    private String descricao;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    @Column(nullable = false)
    private int quantidade;
    @Column(nullable = false)
    private boolean deletado;

    public Produto(String trim, String trimmed, @NotNull BigDecimal preco, @NotNull @Positive Integer quantidade) {}

    public Produto(String nome, String descricao, BigDecimal preco, Integer quantidade, boolean deletado) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.deletado = deletado;
    }

    public void excluir() {
        this.deletado = true;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
    public Integer getQuantidade() { return quantidade; }
    public boolean isDeletado() {
        return deletado;
    }
}
