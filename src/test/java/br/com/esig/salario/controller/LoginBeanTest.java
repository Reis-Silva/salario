package br.com.esig.salario.controller;

import br.com.esig.salario.model.Usuario;
import br.com.esig.salario.service.UsuarioService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginBeanTest {

    @InjectMocks
    private LoginBean loginBean;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private FacesContext facesContext;

    @Mock
    private ExternalContext externalContext;

    @Mock
    private Flash flash;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        loginBean = new LoginBean();
        loginBean.setUsuarioService(usuarioService);

        // Mock FacesContext estático
        FacesContextSetter.setCurrentInstance(facesContext);

        when(facesContext.getExternalContext()).thenReturn(externalContext);
        when(externalContext.getFlash()).thenReturn(flash);
        doNothing().when(flash).setKeepMessages(true);
        doNothing().when(facesContext).addMessage(anyString(), any(FacesMessage.class));

        loginBean.init();
    }

    @AfterEach
    void tearDown() throws Exception {
        FacesContextSetter.setCurrentInstance(null);
        closeable.close();
    }

    @Test
    void logar_DeveAutenticarComSucesso() {
        Usuario usuario = new Usuario(1L, "Julio Cesar", "julio@email.com", "123");
        when(usuarioService.autenticar("julio@email.com", "123")).thenReturn(usuario);

        loginBean.setEmail("julio@email.com");
        loginBean.setSenha("123");

        String resultado = loginBean.logar();

        assertEquals("/pages/home?faces-redirect=true", resultado);
        assertEquals(usuario, loginBean.getUsuarioLogado());
    }

    @Test
    void logar_DeveFalhar_QuandoCredenciaisInvalidas() {
        when(usuarioService.autenticar("invalido@email.com", "senhaErrada")).thenReturn(null);

        loginBean.setEmail("invalido@email.com");
        loginBean.setSenha("senhaErrada");

        String resultado = loginBean.logar();

        assertNull(resultado);
        assertNull(loginBean.getUsuarioLogado());
    }

    @Test
    void cadastrar_DeveCadastrarComSucesso_QuandoDadosValidos() {
        doNothing().when(usuarioService).cadastrar(anyString(), anyString(), anyString());

        loginBean.setNome("Julio");
        loginBean.setEmail("julio@email.com");
        loginBean.setSenha("123");
        loginBean.setConfirmarSenha("123");

        String resultado = loginBean.cadastrar();

        assertEquals("/pages/login?faces-redirect=true", resultado);
    }

    @Test
    void cadastrar_DeveFalhar_QuandoSenhasNaoConferem() {
        loginBean.setNome("Julio");
        loginBean.setEmail("julio@email.com");
        loginBean.setSenha("123");
        loginBean.setConfirmarSenha("456");

        String resultado = loginBean.cadastrar();

        assertNull(resultado);
    }

    @Test
    void cadastrar_DeveFalhar_QuandoEmailInvalido() {
        loginBean.setNome("Julio");
        loginBean.setEmail("email_invalido");
        loginBean.setSenha("123");
        loginBean.setConfirmarSenha("123");

        String resultado = loginBean.cadastrar();

        assertNull(resultado);
    }

    @Test
    void resetarSenha_DeveResetarComSucesso() {
        doNothing().when(usuarioService).resetarSenha("julio@email.com", "novaSenha");

        loginBean.setEmail("julio@email.com");
        loginBean.setSenha("novaSenha");

        String resultado = loginBean.resetarSenha();

        assertEquals("/pages/login?faces-redirect=true", resultado);
    }

    @Test
    void logout_DeveInvalidarSessaoERedirecionar() throws IOException {
        doNothing().when(externalContext).invalidateSession();

        String resultado = loginBean.logout();

        assertEquals("/pages/login.xhtml?faces-redirect=true", resultado);
    }

    private static abstract class FacesContextSetter extends FacesContext {
        protected static void setCurrentInstance(FacesContext context) {
            FacesContext.setCurrentInstance(context);
        }
    }
}
