package br.com.esig.salario.service;

import br.com.esig.salario.model.Usuario;
import br.com.esig.salario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && encoder.matches(senha, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    public void cadastrar(String nome, String email, String senha) {
        if (usuarioRepository.findByEmail(email) != null) {
            throw new RuntimeException("Já existe um usuário cadastrado com este email.");
        }

        Usuario usuario = Usuario.builder()
                .nome(nome)
                .email(email)
                .senha(encoder.encode(senha))
                .build();

        usuarioRepository.save(usuario);
    }

    public void resetarSenha(String email, String novaSenha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Email não encontrado.");
        }

        usuario.setSenha(encoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }
}
