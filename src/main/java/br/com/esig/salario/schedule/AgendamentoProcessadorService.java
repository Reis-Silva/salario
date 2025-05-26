package br.com.esig.salario.schedule;

import br.com.esig.salario.dto.CalculoSalarioConsolidado;
import br.com.esig.salario.model.AgendamentoNovoSalarioCargo;
import br.com.esig.salario.model.Pessoa;
import br.com.esig.salario.model.PessoaSalarioConsolidado;
import br.com.esig.salario.repository.AgendamentoTrocaSalarioCargoRepository;
import br.com.esig.salario.repository.PessoaSalarioConsolidadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoProcessadorService {

    private final AgendamentoTrocaSalarioCargoRepository agendamentoRepository;
    private final PessoaSalarioConsolidadoRepository pessoaSalarioConsolidadoRepository;

    @Async
    @Transactional
    public void processarNovoSalarioCargoAssincrona(List<AgendamentoNovoSalarioCargo> agendamentos) {
        log.info("⏳ Iniciando processamento assíncrono do agendamento...");

        try {
            List<Pessoa> pessoas = agendamentos.stream()
                    .map(AgendamentoNovoSalarioCargo::getPessoa)
                    .toList();

            List<PessoaSalarioConsolidado> consolidados =
                    CalculoSalarioConsolidado.calcularSalarioConsolidado(pessoas);

            pessoaSalarioConsolidadoRepository.saveAll(consolidados);
            agendamentoRepository.deleteAll(agendamentos);

            log.info("✅ Processamento concluído e dados persistidos com sucesso.");
        } catch (Exception e) {
            log.error("❌ Erro ao processar agendamentos de troca de salário/cargo", e);
        }
    }
}
