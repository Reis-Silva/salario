package br.com.esig.salario.service;

import br.com.esig.salario.exception.ValidacaoException;
import br.com.esig.salario.model.Pessoa;
import br.com.esig.salario.repository.PessoaRepository;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Inject
    private PessoaRepository pessoaRepository;

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Pessoa não encontrada com id: " + id));
    }

    public void salvar(Pessoa pessoa) {
        validar(pessoa);
        pessoaRepository.save(pessoa);
    }

    public void excluir(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
    }

    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    public List<Pessoa> pesquisarPorNome(String nome) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome);
    }

    private void validar(Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new ValidacaoException("Nome é obrigatório.");
        }
        if (pessoa.getEmail() == null || pessoa.getEmail().trim().isEmpty()) {
            throw new ValidacaoException("Email é obrigatório.");
        }
        if (pessoa.getCargo() == null || pessoa.getCargo().getId() == null) {
            throw new ValidacaoException("Id do Cargo é Obrigatório");
        }
    }
}
