package br.edu.insper.pi_projsoft.dto;

import br.edu.insper.pi_projsoft.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(Long id, String nome, String descricao, BigDecimal preco, Integer quantidade, boolean deletado) {
    public static ProdutoResponse from(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getDescricao(),
                produto.getPreco(), produto.getQuantidade(),  produto.isDeletado());
    }
}