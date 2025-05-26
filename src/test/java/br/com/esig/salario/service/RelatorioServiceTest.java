package br.com.esig.salario.service;

import br.com.esig.salario.model.PessoaSalarioConsolidado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioServiceTest {

    private RelatorioService relatorioService;

    @BeforeEach
    void setUp() {
        relatorioService = new RelatorioService();
    }

    @Test
    void deveGerarRelatorioComDadosValidos() {
        PessoaSalarioConsolidado pessoa = PessoaSalarioConsolidado.builder()
                .id(1L)
                .nomePessoa("João Silva")
                .nomeCargo("Analista")
                .salario(BigDecimal.valueOf(5000.0))
                .build();

        byte[] pdf = relatorioService.gerarRelatorio(List.of(pessoa));

        assertNotNull(pdf);
        assertTrue(pdf.length > 0, "O PDF gerado deve ter conteúdo.");
    }

    @Test
    void deveLancarExcecaoQuandoListaVazia() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                relatorioService.gerarRelatorio(List.of()));

        assertEquals("A lista de dados não pode estar vazia", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoListaNula() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                relatorioService.gerarRelatorio(null));

        assertEquals("A lista de dados não pode estar vazia", exception.getMessage());
    }
}
