package br.com.esig.salario.service;

import br.com.esig.salario.model.Usuario;
import br.com.esig.salario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void autenticar_DeveRetornarUsuario_QuandoCredenciaisSaoValidas() {
        String email = "teste@email.com";
        String senha = "123456";
        String senhaCriptografada = encoder.encode(senha);

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João")
                .email(email)
                .senha(senhaCriptografada)
                .build();

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        Usuario resultado = usuarioService.autenticar(email, senha);

        assertNotNull(resultado);
        assertEquals(email, resultado.getEmail());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    void autenticar_DeveRetornarNull_QuandoSenhaInvalida() {
        String email = "teste@email.com";
        String senhaCorreta = "123456";
        String senhaErrada = "000000";
        String senhaCriptografada = encoder.encode(senhaCorreta);

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João")
                .email(email)
                .senha(senhaCriptografada)
                .build();

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        Usuario resultado = usuarioService.autenticar(email, senhaErrada);

        assertNull(resultado);
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    void cadastrar_DeveSalvarUsuario_QuandoEmailNaoExiste() {
        String nome = "Maria";
        String email = "maria@email.com";
        String senha = "123456";

        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        usuarioService.cadastrar(nome, email, senha);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());

        Usuario usuarioSalvo = captor.getValue();

        assertEquals(nome, usuarioSalvo.getNome());
        assertEquals(email, usuarioSalvo.getEmail());
        assertTrue(encoder.matches(senha, usuarioSalvo.getSenha()));
    }

    @Test
    void cadastrar_DeveLancarExcecao_QuandoEmailJaExiste() {
        String email = "existe@email.com";

        Usuario usuarioExistente = Usuario.builder()
                .id(1L)
                .email(email)
                .nome("UsuarioExistente")
                .senha("senha")
                .build();

        when(usuarioRepository.findByEmail(email)).thenReturn(usuarioExistente);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                usuarioService.cadastrar("Novo", email, "123456"));

        assertEquals("Já existe um usuário cadastrado com este email.", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void resetarSenha_DeveAtualizarSenha_QuandoEmailExiste() {
        String email = "teste@email.com";
        String novaSenha = "novaSenha";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email(email)
                .nome("Teste")
                .senha(encoder.encode("antiga"))
                .build();

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        usuarioService.resetarSenha(email, novaSenha);

        assertTrue(encoder.matches(novaSenha, usuario.getSenha()));
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void resetarSenha_DeveLancarExcecao_QuandoEmailNaoExiste() {
        String email = "naoexiste@email.com";

        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                usuarioService.resetarSenha(email, "novaSenha"));

        assertEquals("Email não encontrado.", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }
}
