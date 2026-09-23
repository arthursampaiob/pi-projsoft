package br.edu.insper.pi_projsoft.repository;

import br.edu.insper.pi_projsoft.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByDeletadoFalseOrderByNomeAsc();
    List<Produto> findByNomeStartingWithIgnoreCaseAndDeletadoFalseOrderByNomeAsc(String nome);
    List<Produto> findByDeletadoFalseAndNomeStartingWithIgnoreCase(String nome);
    Optional<Produto> findByIdAndDeletadoFalse(Long id);
}
