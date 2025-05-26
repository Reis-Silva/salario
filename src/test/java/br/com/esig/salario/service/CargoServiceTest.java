package br.com.esig.salario.service;

import br.com.esig.salario.model.Cargo;
import br.com.esig.salario.repository.CargoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    private List<Cargo> cargos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cargos = List.of(
                Cargo.builder().id(1L).nome("Estagiario").build(),
                Cargo.builder().id(2L).nome("Tecnico").build(),
                Cargo.builder().id(3L).nome("Analista").build(),
                Cargo.builder().id(4L).nome("Coordenador").build(),
                Cargo.builder().id(5L).nome("Gerente").build()
        );
    }

    @Test
    void deveListarTodosOsCargos() {
        when(cargoRepository.findAll()).thenReturn(cargos);

        List<Cargo> result = cargoService.listarTodos();

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("Estagiario", result.get(0).getNome());
        assertEquals("Gerente", result.get(4).getNome());
        verify(cargoRepository, times(1)).findAll();
    }
}
