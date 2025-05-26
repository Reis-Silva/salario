package br.com.esig.salario.dto;

import br.com.esig.salario.enums.TipoVencimento;
import br.com.esig.salario.model.Cargo;
import br.com.esig.salario.model.CargoVencimento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class VisualizacaoCargoVencimento {

    public static List<String> buscarVisualizacaoCargoVencimento(List<Cargo> cargos) {
        List<String> visualizacaoCargoVencimento = new ArrayList<>();

        cargos.forEach(cargo -> {
            String descricaoCargo = cargo.getNome() + " | ";
            String vencimento = construirVencimento(cargo.getCargoVencimentos());
            visualizacaoCargoVencimento.add(descricaoCargo + vencimento);
        });

        return visualizacaoCargoVencimento;
    }

    private static String construirVencimento(List<CargoVencimento> cargoVencimentos) {
        if (cargoVencimentos == null || cargoVencimentos.isEmpty()) {
            return "Sem vencimentos cadastrados.";
        }

        StringBuilder demonstracaoVencimento = new StringBuilder();
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        for (int i = 0; i < cargoVencimentos.size(); i++) {
            CargoVencimento cv = cargoVencimentos.get(i);

            var vencimento = cv.getVencimento();
            var tipo = vencimento.getTipo();
            var valor = vencimento.getValor();
            var descricao = vencimento.getDescricao();

            String operador = "";

            if (i == 0) {
                operador = "";
            } else if (tipo == TipoVencimento.CREDITO) {
                operador = " + ";
            } else if (tipo == TipoVencimento.DEBITO) {
                operador = " - ";
            }

            demonstracaoVencimento.append(operador)
                    .append(nf.format(valor))
                    .append(" (")
                    .append(descricao)
                    .append(")");
        }

        BigDecimal salarioFinal = CalculoSalarioConsolidado.calcularSalarioBase(cargoVencimentos);

        return "Cálculo Demonstrativo = " + demonstracaoVencimento + " → Total: " + nf.format(salarioFinal);
    }
}
