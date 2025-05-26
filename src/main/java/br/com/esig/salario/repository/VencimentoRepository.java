package br.com.esig.salario.repository;

import br.com.esig.salario.model.Vencimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VencimentoRepository extends JpaRepository<Vencimento, Long> {
}
