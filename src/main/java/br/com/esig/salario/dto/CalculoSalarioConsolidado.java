package br.com.esig.salario.dto;

import br.com.esig.salario.enums.TipoVencimento;
import br.com.esig.salario.model.CargoVencimento;
import br.com.esig.salario.model.Pessoa;
import br.com.esig.salario.model.PessoaSalarioConsolidado;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class CalculoSalarioConsolidado {

    public static List<PessoaSalarioConsolidado> calcularSalarioConsolidado(List<Pessoa> pessoas) {
        List<PessoaSalarioConsolidado> pessoaSalarioConsolidados = new ArrayList<>();

        pessoas.forEach(pessoa -> {
            if (nonNull(pessoa.getCargo())) {
                PessoaSalarioConsolidado pessoaSalarioConsolidado = new PessoaSalarioConsolidado();
                pessoaSalarioConsolidado.setPessoaId(pessoa.getId());
                pessoaSalarioConsolidado.setNomePessoa(pessoa.getNome());
                pessoaSalarioConsolidado.setNomeCargo(pessoa.getCargo().getNome());
                pessoaSalarioConsolidado.setSalario(calcularSalarioBase(pessoa.getCargo().getCargoVencimentos()));
                pessoaSalarioConsolidados.add(pessoaSalarioConsolidado);
            }
        });

        return pessoaSalarioConsolidados;
    }

    public static BigDecimal calcularSalarioBase(List<CargoVencimento> cargoVencimentos) {
        return cargoVencimentos.stream()
                .map(cargoVencimento -> {
                    BigDecimal valor = cargoVencimento.getVencimento().getValor();
                    if (Objects.equals(TipoVencimento.CREDITO, cargoVencimento.getVencimento().getTipo())) {
                        return valor;
                    } else if (Objects.equals(TipoVencimento.DEBITO, cargoVencimento.getVencimento().getTipo())) {
                        return valor.negate();
                    } else {
                        return BigDecimal.ZERO;
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

