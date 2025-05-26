package br.com.esig.salario.service;

import br.com.esig.salario.exception.ValidacaoException;
import br.com.esig.salario.model.Cargo;
import br.com.esig.salario.model.Pessoa;
import br.com.esig.salario.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Cargo cargo = Cargo.builder()
                .id(1L)
                .nome("Analista")
                .build();

        pessoa = Pessoa.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .cidade("Belém")
                .cep("66000000")
                .endereco("Rua A")
                .pais("Brasil")
                .usuario("joaos")
                .telefone("9199999999")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .cargo(cargo)
                .build();
    }

    @Test
    void deveBuscarPorId() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        Pessoa resultado = pessoaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoBuscarIdInexistente() {
        when(pessoaRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                pessoaService.buscarPorId(2L));

        assertEquals("Pessoa não encontrada com id: 2", exception.getMessage());
        verify(pessoaRepository, times(1)).findById(2L);
    }

    @Test
    void deveSalvarPessoaComDadosValidos() {
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        assertDoesNotThrow(() -> pessoaService.salvar(pessoa));

        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void deveLancarExcecaoQuandoSalvarSemNome() {
        pessoa.setNome(null);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () ->
                pessoaService.salvar(pessoa));

        assertEquals("Nome é obrigatório.", exception.getMessage());
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoSalvarSemEmail() {
        pessoa.setEmail(null);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () ->
                pessoaService.salvar(pessoa));

        assertEquals("Email é obrigatório.", exception.getMessage());
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoSalvarSemCargo() {
        pessoa.setCargo(null);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () ->
                pessoaService.salvar(pessoa));

        assertEquals("Id do Cargo é Obrigatório", exception.getMessage());
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void deveExcluirPessoa() {
        pessoaService.excluir(pessoa);

        verify(pessoaRepository, times(1)).delete(pessoa);
    }

    @Test
    void deveListarTodasAsPessoas() {
        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        List<Pessoa> resultado = pessoaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    void devePesquisarPorNome() {
        when(pessoaRepository.findByNomeContainingIgnoreCase("joão"))
                .thenReturn(List.of(pessoa));

        List<Pessoa> resultado = pessoaService.pesquisarPorNome("joão");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
        verify(pessoaRepository, times(1)).findByNomeContainingIgnoreCase("joão");
    }
}
