package br.com.esig.salario.schedule;

import br.com.esig.salario.model.AgendamentoNovoSalarioCargo;
import br.com.esig.salario.repository.AgendamentoTrocaSalarioCargoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoProcessadorService processadorService;
    private final AgendamentoTrocaSalarioCargoRepository agendamentoRepository;

    @Scheduled(fixedDelay = 30000)
    public void executarAgendamentos() {
        log.info("🔄 Verificando agendamentos de troca de salário/cargo...");

        List<AgendamentoNovoSalarioCargo> agendamentos = agendamentoRepository.findAll();

        if (!agendamentos.isEmpty()) {
            processadorService.processarNovoSalarioCargoAssincrona(agendamentos);
        } else {
            log.info("ℹ️ Nenhum agendamento pendente encontrado.");
        }
    }
}
