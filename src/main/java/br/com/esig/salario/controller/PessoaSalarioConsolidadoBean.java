package br.com.esig.salario.controller;

import br.com.esig.salario.model.AgendamentoNovoSalarioCargo;
import br.com.esig.salario.model.Pessoa;
import br.com.esig.salario.model.PessoaSalarioConsolidado;
import br.com.esig.salario.service.AgendamentoSalarioCargoService;
import br.com.esig.salario.service.PessoaSalarioConsolidadoService;
import br.com.esig.salario.service.RelatorioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class PessoaSalarioConsolidadoBean implements Serializable {

    private List<PessoaSalarioConsolidado> pessoaSalarioConsolidados;

    private PessoaSalarioConsolidado pessoaSalarioConsolidado;

    private List<Pessoa> pessoas;

    private String filtroNome;

    @Inject
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    @Inject
    private RelatorioService relatorioService;

    @Inject
    private AgendamentoSalarioCargoService agendamentoSalarioCargoService;

    @PostConstruct
    public void init() {
        List<AgendamentoNovoSalarioCargo> agendamentos = agendamentoSalarioCargoService.listarTodos();

        if (agendamentos.isEmpty()) {
            this.pessoaSalarioConsolidados = pessoaSalarioConsolidadoService.listarTodas();
        }
    }

    public String editar(Pessoa pessoa) {
        return "/pages/lista-pessoas-salario-consolidado?faces-redirect=true&id=" + pessoa.getId();
    }

    public void limpar() {
        this.pessoaSalarioConsolidado = new PessoaSalarioConsolidado();
    }

    public void pesquisar() {
        if (filtroNome != null && !filtroNome.isEmpty()) {
            this.pessoaSalarioConsolidados = pessoaSalarioConsolidadoService.pesquisarPorNome(filtroNome);
        } else {
            listarTodos();
        }
    }

    public void exportarPdf() {
        try {
            if (pessoaSalarioConsolidados == null || pessoaSalarioConsolidados.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Atenção", "Não há dados para gerar o relatório."));
                return;
            }

            byte[] pdf = relatorioService.gerarRelatorio(pessoaSalarioConsolidados);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();

            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition",
                    "attachment; filename=relatorio_salario_consolidado.pdf");
            externalContext.setResponseContentLength(pdf.length);

            try (OutputStream outputStream = externalContext.getResponseOutputStream()) {
                outputStream.write(pdf);
                outputStream.flush();
            }

            facesContext.responseComplete();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro", "Erro ao gerar relatório: " + e.getMessage()));
        }
    }

    private void listarTodos() {
        this.pessoaSalarioConsolidados = pessoaSalarioConsolidadoService.listarTodas();
    }
}

