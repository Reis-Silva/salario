package br.com.esig.salario.security;

import br.com.esig.salario.controller.LoginBean;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.inject.Named;

import java.io.IOException;

@Named
public class AutorizacaoListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();

        String pagina = context.getViewRoot().getViewId();

        boolean paginaPublica = pagina.contains("login.xhtml")
                || pagina.contains("cadastro-usuario.xhtml")
                || pagina.contains("recuperar-senha.xhtml");

        LoginBean loginBean = context.getApplication()
                .evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);

        if (!paginaPublica && (loginBean == null || !loginBean.isLogado())) {
            try {
                context.getExternalContext().redirect(
                        context.getExternalContext().getRequestContextPath() + "/pages/login.xhtml");
            } catch (IOException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Erro", "Erro na autorização."));
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
