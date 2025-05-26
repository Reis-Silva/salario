package br.com.esig.salario.service;

import br.com.esig.salario.exception.ValidacaoException;
import br.com.esig.salario.model.PessoaSalarioConsolidado;
import br.com.esig.salario.repository.PessoaSalarioConsolidadoRepository;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class PessoaSalarioConsolidadoService {

    @Inject
    private PessoaSalarioConsolidadoRepository pessoaSalarioConsolidadoRepository;

    public PessoaSalarioConsolidado buscarPorId(Long id) {
        return pessoaSalarioConsolidadoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Pessoa não encontrada com id: " + id));
    }

    public void salvar(PessoaSalarioConsolidado pessoaSalarioConsolidado) {
        validar(pessoaSalarioConsolidado);
        pessoaSalarioConsolidadoRepository.save(pessoaSalarioConsolidado);
    }

    public void excluir(PessoaSalarioConsolidado pessoa) {
        pessoaSalarioConsolidadoRepository.delete(pessoa);
    }

    public List<PessoaSalarioConsolidado> listarTodas() {
        return pessoaSalarioConsolidadoRepository.findAll();
    }

    public List<PessoaSalarioConsolidado> pesquisarPorNome(String nome) {
        return pessoaSalarioConsolidadoRepository.findByNomePessoaContainingIgnoreCase(nome);
    }

    public boolean verificarExistenciaPorPessoaId(Long pessoaId) {
        return pessoaSalarioConsolidadoRepository.existsByPessoaId(pessoaId);
    }

    public void deletarPorPessoaId(Long pessoaId) {
        pessoaSalarioConsolidadoRepository.deleteByPessoaId(pessoaId);
    }

    private void validar(PessoaSalarioConsolidado pessoaSalarioConsolidado) {
        if (isNull(pessoaSalarioConsolidado.getPessoaId())) {
            throw new ValidacaoException("PessoaId é obrigatório.");
        }
        if (isNull(pessoaSalarioConsolidado.getNomePessoa()) || pessoaSalarioConsolidado.getNomePessoa().trim().isEmpty()) {
            throw new ValidacaoException("Nome é obrigatório.");
        }
        if (isNull(pessoaSalarioConsolidado.getNomeCargo()) || pessoaSalarioConsolidado.getNomeCargo().trim().isEmpty()) {
            throw new ValidacaoException("Email é obrigatório.");
        }
    }


}
