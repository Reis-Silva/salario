package br.com.esig.salario.service;

import br.com.esig.salario.exception.ValidacaoException;
import br.com.esig.salario.model.PessoaSalarioConsolidado;
import br.com.esig.salario.repository.PessoaSalarioConsolidadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaSalarioConsolidadoServiceTest {

    @InjectMocks
    private PessoaSalarioConsolidadoService service;

    @Mock
    private PessoaSalarioConsolidadoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private PessoaSalarioConsolidado criarMock() {
        return PessoaSalarioConsolidado.builder()
                .id(1L)
                .pessoaId(100L)
                .nomePessoa("Julio Developer")
                .nomeCargo("Desenvolvedor")
                .salario(BigDecimal.valueOf(10000))
                .build();
    }

    @Test
    void deveSalvarComSucesso() {
        PessoaSalarioConsolidado mock = criarMock();

        service.salvar(mock);

        verify(repository, times(1)).save(mock);
    }

    @Test
    void deveLancarExcecaoQuandoPessoaIdForNulo() {
        PessoaSalarioConsolidado mock = criarMock();
        mock.setPessoaId(null);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> service.salvar(mock));

        assertEquals("PessoaId é obrigatório.", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {
        PessoaSalarioConsolidado mock = criarMock();
        mock.setNomePessoa(null);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> service.salvar(mock));

        assertEquals("Nome é obrigatório.", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCargoForNulo() {
        PessoaSalarioConsolidado mock = criarMock();
        mock.setNomeCargo(null);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> service.salvar(mock));

        assertEquals("Email é obrigatório.", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        PessoaSalarioConsolidado mock = criarMock();

        when(repository.findById(1L)).thenReturn(Optional.of(mock));

        PessoaSalarioConsolidado resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Julio Developer", resultado.getNomePessoa());
    }

    @Test
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.buscarPorId(99L));

        assertEquals("Pessoa não encontrada com id: 99", exception.getMessage());
    }

    @Test
    void deveListarTodas() {
        when(repository.findAll()).thenReturn(List.of(criarMock()));

        List<PessoaSalarioConsolidado> lista = service.listarTodas();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void devePesquisarPorNome() {
        when(repository.findByNomePessoaContainingIgnoreCase("Julio")).thenReturn(List.of(criarMock()));

        List<PessoaSalarioConsolidado> lista = service.pesquisarPorNome("Julio");

        assertFalse(lista.isEmpty());
        assertEquals("Julio Developer", lista.get(0).getNomePessoa());
    }

    @Test
    void deveVerificarExistenciaPorPessoaId() {
        when(repository.existsByPessoaId(100L)).thenReturn(true);

        boolean existe = service.verificarExistenciaPorPessoaId(100L);

        assertTrue(existe);
    }

    @Test
    void deveDeletarPorPessoaId() {
        doNothing().when(repository).deleteByPessoaId(100L);

        service.deletarPorPessoaId(100L);

        verify(repository, times(1)).deleteByPessoaId(100L);
    }

    @Test
    void deveExcluirComSucesso() {
        PessoaSalarioConsolidado mock = criarMock();

        doNothing().when(repository).delete(mock);

        service.excluir(mock);

        verify(repository, times(1)).delete(mock);
    }
}
