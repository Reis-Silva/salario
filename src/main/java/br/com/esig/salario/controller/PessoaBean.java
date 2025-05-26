package br.com.esig.salario.controller;

import br.com.esig.salario.dto.VisualizacaoCargoVencimento;
import br.com.esig.salario.exception.ValidacaoException;
import br.com.esig.salario.model.AgendamentoNovoSalarioCargo;
import br.com.esig.salario.model.Cargo;
import br.com.esig.salario.model.Pessoa;
import br.com.esig.salario.service.AgendamentoSalarioCargoService;
import br.com.esig.salario.service.CargoService;
import br.com.esig.salario.service.PessoaSalarioConsolidadoService;
import br.com.esig.salario.service.PessoaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.esig.salario.util.FacesUtil.*;
import static java.util.Objects.nonNull;

@Named
@ViewScoped
@Getter
@Setter
public class PessoaBean implements Serializable {

    private Pessoa pessoa;
    private List<Pessoa> pessoas;
    private String filtroNome;
    private List<String> cargosVencimentos;
    private List<Cargo> cargos;
    private AgendamentoNovoSalarioCargo agendamentoNovoSalarioCargo;

    @Inject
    private PessoaService pessoaService;

    @Inject
    private CargoService cargoService;

    @Inject
    private AgendamentoSalarioCargoService agendamentoSalarioCargoService;

    @Inject
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    @PostConstruct
    public void init() {
        String idParam = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("id");

        if (idParam != null) {
            Long id = Long.valueOf(idParam);
            this.pessoa = pessoaService.buscarPorId(id);
        } else {
            limpar();
        }
        listarTodos();
        carregarCargos();
    }

    public String salvar() {
        try {
            boolean isNovo = (pessoa.getId() == null);
            boolean isAgendamentoSalarioCargo = false;

            if (!isNovo) {
                Pessoa pessoaBanco = pessoaService.buscarPorId(pessoa.getId());
                System.out.println("teste");
                if (nonNull(pessoaBanco) && !houveAlteracao(pessoaBanco, pessoa)) {
                    addInfoMessage("Nenhuma alteração detectada.");
                    return "/pages/lista-pessoas?faces-redirect=true";
                }

                if (nonNull(pessoaBanco) && !Objects.equals(pessoaBanco.getCargo().getId(), pessoa.getCargo().getId())) {
                    isAgendamentoSalarioCargo = true;
                }
            }

            pessoaService.salvar(pessoa);

            if (isNovo) {
                addInfoMessage("Pessoa salva com sucesso!");
                isAgendamentoSalarioCargo = true;
            } else {
                addInfoMessage("Pessoa atualizada com sucesso!");
            }

            if (isAgendamentoSalarioCargo) {
                Pessoa pessoaBanco = pessoaService.buscarPorId(pessoa.getId());
                agendamentoNovoSalarioCargo = new AgendamentoNovoSalarioCargo();
                agendamentoNovoSalarioCargo.setPessoa(pessoaBanco);
                agendamentoNovoSalarioCargo.setCargoAtual(pessoaBanco.getCargo());
                agendamentoNovoSalarioCargo.setCargoTroca(pessoa.getCargo());
                agendamentoSalarioCargoService.salvar(agendamentoNovoSalarioCargo);
            }
            listarTodos();
            return "/pages/lista-pessoas?faces-redirect=true";

        } catch (ValidacaoException e) {
            addWarnMessage(e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar pessoa.");
            e.printStackTrace();
            return null;
        }
    }

    public String editar(Pessoa pessoa) {
        return "/pages/form-pessoa?faces-redirect=true&id=" + pessoa.getId();
    }

    public void excluir(Pessoa pessoa) {
        try {
            pessoaService.excluir(pessoa);

            if (pessoaSalarioConsolidadoService.verificarExistenciaPorPessoaId(pessoa.getId())) {
                pessoaSalarioConsolidadoService.deletarPorPessoaId(pessoa.getId());
            }

            addInfoMessage("Pessoa excluída com sucesso!");
            listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            addErrorMessage("Erro ao excluir pessoa.");
        }
    }

    public void limpar() {
        this.pessoa = new Pessoa();
        this.pessoa.setCargo(new Cargo());
        this.cargosVencimentos = new ArrayList<>();
    }

    public void pesquisar() {
        if (filtroNome != null && !filtroNome.isEmpty()) {
            this.pessoas = pessoaService.pesquisarPorNome(filtroNome);
        } else {
            listarTodos();
        }
    }

    public void buscarCargoVencimento() {
        this.cargosVencimentos = VisualizacaoCargoVencimento.buscarVisualizacaoCargoVencimento(cargoService.listarTodos());
    }

    private void listarTodos() {
        this.pessoas = pessoaService.listarTodas();
    }

    private void carregarCargos() {
        this.cargos = cargoService.listarTodos();
    }

    private boolean houveAlteracao(Pessoa antigo, Pessoa novo) {
        if (!Objects.equals(antigo.getNome(), novo.getNome())) return true;
        if (!Objects.equals(antigo.getCidade(), novo.getCidade())) return true;
        if (!Objects.equals(antigo.getEmail(), novo.getEmail())) return true;
        if (!Objects.equals(antigo.getCep(), novo.getCepSemMascara())) return true;
        if (!Objects.equals(antigo.getEndereco(), novo.getEndereco())) return true;
        if (!Objects.equals(antigo.getPais(), novo.getPais())) return true;
        if (!Objects.equals(antigo.getUsuario(), novo.getUsuario())) return true;
        if (!Objects.equals(antigo.getTelefone(), novo.getTelefoneSemMascara())) return true;
        if (!Objects.equals(antigo.getDataNascimento(), novo.getDataNascimento())) return true;
        return !Objects.equals(
                antigo.getCargo() != null ? antigo.getCargo().getId() : null,
                novo.getCargo() != null ? novo.getCargo().getId() : null
        );
    }
}
