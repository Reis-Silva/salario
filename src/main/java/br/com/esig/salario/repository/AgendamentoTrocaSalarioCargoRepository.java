package br.com.esig.salario.repository;

import br.com.esig.salario.model.AgendamentoNovoSalarioCargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoTrocaSalarioCargoRepository extends JpaRepository<AgendamentoNovoSalarioCargo, Long> {
}
