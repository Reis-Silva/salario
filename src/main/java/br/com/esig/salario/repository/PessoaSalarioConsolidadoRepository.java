package br.com.esig.salario.repository;

import br.com.esig.salario.model.PessoaSalarioConsolidado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaSalarioConsolidadoRepository extends JpaRepository<PessoaSalarioConsolidado, Long> {

    List<PessoaSalarioConsolidado> findByNomePessoaContainingIgnoreCase(String nome);

    boolean existsByPessoaId(Long pessoaId);

    void deleteByPessoaId(Long pessoaId);
}
