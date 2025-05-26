package br.com.esig.salario.controller;

import br.com.esig.salario.model.Pessoa;
import br.com.esig.salario.model.PessoaSalarioConsolidado;
import br.com.esig.salario.service.AgendamentoSalarioCargoService;
import br.com.esig.salario.service.PessoaSalarioConsolidadoService;
import br.com.esig.salario.service.RelatorioService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PessoaSalarioConsolidadoBeanTest {

    private PessoaSalarioConsolidadoBean bean;

    @Mock
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;
    @Mock
    private AgendamentoSalarioCargoService agendamentoSalarioCargoService;
    @Mock
    private RelatorioService relatorioService;
    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;
    @Mock
    private OutputStream outputStream;
    @Mock
    private UIViewRoot uiViewRoot;

    private MockedStatic<FacesContext> facesContextStatic;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);

        facesContextStatic = mockStatic(FacesContext.class);
        facesContextStatic.when(FacesContext::getCurrentInstance).thenReturn(facesContext);
        when(facesContext.getExternalContext()).thenReturn(externalContext);
        when(facesContext.getViewRoot()).thenReturn(uiViewRoot);
        when(uiViewRoot.getLocale()).thenReturn(Locale.getDefault());

        bean = new PessoaSalarioConsolidadoBean();
        bean.setPessoaSalarioConsolidadoService(pessoaSalarioConsolidadoService);
        bean.setAgendamentoSalarioCargoService(agendamentoSalarioCargoService);
        bean.setRelatorioService(relatorioService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
        facesContextStatic.close();
    }

    @Test
    void deveInicializarQuandoNaoTemAgendamento() {
        when(agendamentoSalarioCargoService.listarTodos()).thenReturn(Collections.emptyList());
        when(pessoaSalarioConsolidadoService.listarTodas()).thenReturn(List.of(new PessoaSalarioConsolidado()));

        bean.init();

        assertNotNull(bean.getPessoaSalarioConsolidados());
        assertEquals(1, bean.getPessoaSalarioConsolidados().size());
    }

    @Test
    void devePesquisarPorNome() {
        bean.setFiltroNome("João");
        when(pessoaSalarioConsolidadoService.pesquisarPorNome("João"))
                .thenReturn(List.of(new PessoaSalarioConsolidado()));

        bean.pesquisar();

        assertNotNull(bean.getPessoaSalarioConsolidados());
        assertEquals(1, bean.getPessoaSalarioConsolidados().size());
    }

    @Test
    void deveListarTodosQuandoFiltroVazio() {
        bean.setFiltroNome("");
        when(pessoaSalarioConsolidadoService.listarTodas())
                .thenReturn(List.of(new PessoaSalarioConsolidado()));

        bean.pesquisar();

        assertNotNull(bean.getPessoaSalarioConsolidados());
        assertEquals(1, bean.getPessoaSalarioConsolidados().size());
    }

    @Test
    void deveLimparPessoaSalarioConsolidado() {
        bean.limpar();
        assertNotNull(bean.getPessoaSalarioConsolidado());
    }

    @Test
    void deveEditarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(123L);

        String resultado = bean.editar(pessoa);

        assertEquals("/pages/lista-pessoas-salario-consolidado?faces-redirect=true&id=123", resultado);
    }

    @Test
    void deveExportarPdfComSucesso() throws Exception {
        bean.setPessoaSalarioConsolidados(List.of(new PessoaSalarioConsolidado()));

        byte[] pdf = "pdf-content".getBytes();
        when(relatorioService.gerarRelatorio(anyList())).thenReturn(pdf);
        when(externalContext.getResponseOutputStream()).thenReturn(outputStream);

        bean.exportarPdf();

        verify(outputStream, times(1)).write(pdf);
        verify(outputStream, times(1)).flush();
        verify(facesContext, times(1)).responseComplete();
    }

    @Test
    void naoDeveExportarPdfQuandoListaVazia() {
        bean.setPessoaSalarioConsolidados(Collections.emptyList());

        bean.exportarPdf();

        verify(facesContext, times(1))
                .addMessage(isNull(), argThat(message ->
                        message.getSeverity().equals(FacesMessage.SEVERITY_WARN) &&
                                message.getSummary().equals("Atenção")
                ));
    }

    @Test
    void deveExibirErroAoExportarPdf() throws Exception {
        bean.setPessoaSalarioConsolidados(List.of(new PessoaSalarioConsolidado()));

        when(relatorioService.gerarRelatorio(anyList()))
                .thenThrow(new RuntimeException("Erro simulado"));

        bean.exportarPdf();

        verify(facesContext, times(1))
                .addMessage(isNull(), argThat(message ->
                        message.getSeverity().equals(FacesMessage.SEVERITY_ERROR) &&
                                message.getSummary().equals("Erro")
                ));
    }
}
