package br.com.esig.salario.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FacesUtil {

    public static void addInfoMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Informação", message);
    }

    public static void addWarnMessage(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Atenção", message);
    }

    public static void addErrorMessage(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Erro", message);
    }

    public static void putInFlash(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(key, value);
    }

    public static Object getFromFlash(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key);
    }

    private static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesMessage facesMessage = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
