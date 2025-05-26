package br.com.esig.salario.controller;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Locale;

import static org.mockito.Mockito.*;

class PessoaBeanTest {

    private PessoaBean pessoaBean;

    @Mock
    private FacesContext facesContext;

    private MockedStatic<FacesContext> facesContextStatic;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        facesContextStatic = mockStatic(FacesContext.class);
        facesContextStatic.when(FacesContext::getCurrentInstance).thenReturn(facesContext);

        var viewRoot = mock(jakarta.faces.component.UIViewRoot.class);
        when(facesContext.getViewRoot()).thenReturn(viewRoot);
        when(viewRoot.getLocale()).thenReturn(Locale.getDefault());

        pessoaBean = new PessoaBean();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
        if (facesContextStatic != null) {
            facesContextStatic.close();
        }
    }

    @Test
    void deveAdicionarMensagemDeSucesso() {
        pessoaBean.adicionarMensagem("Operação realizada com sucesso!", FacesMessage.SEVERITY_INFO);

        verify(facesContext, times(1))
                .addMessage(isNull(), argThat(message ->
                        message.getSummary().equals("Operação realizada com sucesso!") &&
                                message.getSeverity().equals(FacesMessage.SEVERITY_INFO)
                ));
    }

    @Test
    void deveAdicionarMensagemDeErro() {
        pessoaBean.adicionarMensagem("Erro na operação!", FacesMessage.SEVERITY_ERROR);

        verify(facesContext, times(1))
                .addMessage(isNull(), argThat(message ->
                        message.getSummary().equals("Erro na operação!") &&
                                message.getSeverity().equals(FacesMessage.SEVERITY_ERROR)
                ));
    }

    static class PessoaBean {
        public void adicionarMensagem(String mensagem, FacesMessage.Severity severity) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, mensagem, null));
        }
    }
}
