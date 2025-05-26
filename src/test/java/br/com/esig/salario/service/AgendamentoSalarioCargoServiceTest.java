package br.com.esig.salario.service;

import br.com.esig.salario.model.AgendamentoNovoSalarioCargo;
import br.com.esig.salario.model.Cargo;
import br.com.esig.salario.model.Pessoa;
import br.com.esig.salario.repository.AgendamentoTrocaSalarioCargoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoSalarioCargoServiceTest {

    @InjectMocks
    private AgendamentoSalarioCargoService service;

    @Mock
    private AgendamentoTrocaSalarioCargoRepository repository;

    private AgendamentoNovoSalarioCargo agendamento;
    private Pessoa pessoa;
    private Cargo cargoAtual;
    private Cargo cargoTroca;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pessoa = Pessoa.builder()
                .id(1L)
                .nome("Julio Cesar")
                .build();

        cargoAtual = Cargo.builder()
                .id(2L) // Técnico
                .nome("Técnico")
                .build();

        cargoTroca = Cargo.builder()
                .id(4L) // Coordenador
                .nome("Coordenador")
                .build();

        agendamento = AgendamentoNovoSalarioCargo.builder()
                .id(1L)
                .pessoa(pessoa)
                .cargoAtual(cargoAtual)
                .cargoTroca(cargoTroca)
                .build();
    }

    @Test
    void deveListarTodosOsAgendamentos() {
        when(repository.findAll()).thenReturn(List.of(agendamento));

        List<AgendamentoNovoSalarioCargo> result = service.listarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Julio Cesar", result.get(0).getPessoa().getNome());
        assertEquals("Técnico", result.get(0).getCargoAtual().getNome());
        assertEquals("Coordenador", result.get(0).getCargoTroca().getNome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarPorIdQuandoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));

        Optional<AgendamentoNovoSalarioCargo> result = service.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Julio Cesar", result.get().getPessoa().getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrarPorId() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<AgendamentoNovoSalarioCargo> result = service.buscarPorId(99L);

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void deveSalvarAgendamento() {
        when(repository.save(agendamento)).thenReturn(agendamento);

        AgendamentoNovoSalarioCargo result = service.salvar(agendamento);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Julio Cesar", result.getPessoa().getNome());
        assertEquals("Técnico", result.getCargoAtual().getNome());
        assertEquals("Coordenador", result.getCargoTroca().getNome());
        verify(repository, times(1)).save(agendamento);
    }

    @Test
    void deveExcluirPorId() {
        doNothing().when(repository).deleteById(1L);

        service.excluir(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
