package br.com.esig.salario.repository;

import br.com.esig.salario.model.CargoVencimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoVencimentoRepository extends JpaRepository<CargoVencimento, Long> {
}
