package br.com.esig.salario.controller;

import br.com.esig.salario.model.Usuario;
import br.com.esig.salario.service.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.regex.Pattern;

import static br.com.esig.salario.util.FacesUtil.*;

@Named
@SessionScoped
@Getter
@Setter
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;
    private String confirmarSenha;
    private String nome;
    private Usuario usuarioLogado;

    @Inject
    private UsuarioService usuarioService;

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    @PostConstruct
    public void init() {
        limparCampos();
    }

    public String logar() {
        Usuario usuario = usuarioService.autenticar(email, senha);
        if (usuario != null) {
            usuarioLogado = usuario;
            return "/pages/home?faces-redirect=true";
        }

        addWarnMessage("Email ou senha inválidos");
        return null;
    }

    public String cadastrar() {
        try {
            if (!senha.equals(confirmarSenha)) {
                throw new RuntimeException("As senhas não coincidem.");
            }

            if (!EMAIL_REGEX.matcher(email).matches()) {
                throw new RuntimeException("Email inválido. Use o formato exemplo@dominio.com");
            }

            usuarioService.cadastrar(nome, email, senha);

            addInfoMessage("Usuário cadastrado com sucesso!");
            limparCampos();
            return "/pages/login?faces-redirect=true";

        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Atenção", e.getMessage()));
            return null;
        }
    }

    public String resetarSenha() {
        try {
            usuarioService.resetarSenha(email, senha);

            addInfoMessage("Senha Alterada com Sucesso!");
            return "/pages/login?faces-redirect=true";
        } catch (RuntimeException e) {
            addErrorMessage("Erro ao resetar senha.");
            e.printStackTrace();
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/pages/login.xhtml?faces-redirect=true";
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }

    public void verificarLogin() {
        if (isPaginaPublica()) return;

        if (usuarioLogado == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("/pages/login.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPaginaPublica() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId.contains("login.xhtml") ||
                viewId.contains("cadastro-usuario.xhtml") ||
                viewId.contains("esqueci-senha.xhtml");
    }

    private void limparCampos() {
        nome = "";
        email = "";
        senha = "";
        confirmarSenha = "";
    }
}
