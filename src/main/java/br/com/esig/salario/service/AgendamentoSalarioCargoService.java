package br.com.esig.salario.service;

import br.com.esig.salario.model.AgendamentoNovoSalarioCargo;
import br.com.esig.salario.repository.AgendamentoTrocaSalarioCargoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgendamentoSalarioCargoService {

    private final AgendamentoTrocaSalarioCargoRepository repository;

    public List<AgendamentoNovoSalarioCargo> listarTodos() {
        return repository.findAll();
    }

    public Optional<AgendamentoNovoSalarioCargo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public AgendamentoNovoSalarioCargo salvar(AgendamentoNovoSalarioCargo agendamento) {
        return repository.save(agendamento);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
