package br.com.esig.salario.controller;

import br.com.esig.salario.model.AgendamentoNovoSalarioCargo;
import br.com.esig.salario.service.AgendamentoSalarioCargoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

import static br.com.esig.salario.util.FacesUtil.addWarnMessage;

@Named
@RequestScoped
public class NavegacaoBean implements Serializable {

    @Autowired
    private AgendamentoSalarioCargoService agendamentoSalarioCargoService;

    public String abrirTelaSalarioConsolidado() {
        List<AgendamentoNovoSalarioCargo> agendamentos = agendamentoSalarioCargoService.listarTodos();

        if (!agendamentos.isEmpty()) {
            addWarnMessage("Aguarde! - Ainda Restam cálculos de Salários Pendentes. Fique Atualizando a Página.");
        }

        return "/pages/lista-pessoas-salario-consolidado?faces-redirect=true";
    }
}
