package br.edu.insper.pi_projsoft.service;

import br.edu.insper.pi_projsoft.dto.CriarProdutoRequest;
import br.edu.insper.pi_projsoft.model.Produto;
import br.edu.insper.pi_projsoft.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {
    @Mock ProdutoRepository repository;
    @InjectMocks ProdutoService service;
    private Produto produto;

    @BeforeEach
    void setUp() { produto = new Produto("bola", "bola para jogar fut", new BigDecimal("10.00"), 20, false); }

    @Test
    public void listaTodosQuandoFiltroNulo() {
        when(repository.findAllByDeletadoFalseOrderByNomeAsc()).thenReturn(List.of(produto));
        Assertions.assertThat(service.listar(null)).extracting("nome").containsExactly("bola");
    }

    @Test void listaTodosQuandoFiltroEmBranco() {
        when(repository.findAllByDeletadoFalseOrderByNomeAsc()).thenReturn(List.of());
        Assertions.assertThat(service.listar("  ")).isEmpty();
    }

    @Test void listaPorPrefixoNormalizado() {
        when(repository.findByNomeStartingWithIgnoreCaseAndDeletadoFalseOrderByNomeAsc("bo"))
                .thenReturn(List.of(produto));
        Assertions.assertThat(service.listar(" bo ")).hasSize(1);
    }

    @Test void criaPoduto() {
        var request = new CriarProdutoRequest("bola", "bola para jogar fut", new BigDecimal("10.00"), 20, false);
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var response = service.criar(request);
        Assertions.assertThat(response.nome()).isEqualTo("bola");
        Assertions.assertThat(response.descricao()).isEqualTo("bola para jogar fut");
        Assertions.assertThat(response.preco()).isEqualTo(new BigDecimal("10.00"));
        Assertions.assertThat(response.quantidade()).isEqualTo(20);
        Assertions.assertThat(response.deletado()).isEqualTo(false);
    }

    @Test void excluiLogicamenteProdutoExistente() {
        when(repository.findByIdAndDeletadoFalse(1L)).thenReturn(Optional.of(produto));
        service.excluir(1L);
        verify(repository).findByIdAndDeletadoFalse(1L);
    }

    @Test void falhaAoExcluirProdutoInexistente() {
        when(repository.findByIdAndDeletadoFalse(99L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> service.excluir(99L))
                .isInstanceOf(EntityNotFoundException.class).hasMessage("Produto não encontrado");
    }
}