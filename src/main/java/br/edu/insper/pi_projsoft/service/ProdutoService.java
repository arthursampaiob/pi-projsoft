package br.edu.insper.pi_projsoft.service;

import br.edu.insper.pi_projsoft.dto.CriarProdutoRequest;
import br.edu.insper.pi_projsoft.dto.ProdutoResponse;
import br.edu.insper.pi_projsoft.model.Produto;
import br.edu.insper.pi_projsoft.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) { this.repository = repository; }

    public List<ProdutoResponse> listar(String nome) {
        var produto = nome == null || nome.isBlank()
                ? repository.findAllByDeletadoFalseOrderByNomeAsc()
                : repository.findByNomeStartingWithIgnoreCaseAndDeletadoFalseOrderByNomeAsc(nome.trim());
        return produto.stream().map(ProdutoResponse::from).toList();
    }

    public ProdutoResponse criar(CriarProdutoRequest request) {
        var produto = new Produto(request.nome().trim(), request.descricao().trim(),
                request.preco(), request.quantidade(), request.deletado());
        return ProdutoResponse.from(repository.save(produto));
    }

    public void excluir(Long id) {
        var produto = repository.findByIdAndDeletadoFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        produto.excluir();
    }
}
