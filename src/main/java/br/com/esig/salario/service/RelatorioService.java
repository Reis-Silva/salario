package br.com.esig.salario.service;

import br.com.esig.salario.model.PessoaSalarioConsolidado;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    private static final String CAMINHO_RELATORIO = "/relatorios/relatorio_pessoa_salario_consolidado.jrxml";

    public byte[] gerarRelatorio(List<PessoaSalarioConsolidado> dados) {
        if (dados == null || dados.isEmpty()) {
            throw new IllegalArgumentException("A lista de dados não pode estar vazia");
        }

        try {
            InputStream relatorioStream = this.getClass().getResourceAsStream(CAMINHO_RELATORIO);

            if (relatorioStream == null) {
                throw new IllegalArgumentException("Arquivo do relatório não encontrado.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(relatorioStream);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("titulo", "Relatório de Salário Consolidado");
            parametros.put("data_emissao",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm:ss")));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            throw new RuntimeException("Erro ao processar o relatório: " + e.getMessage(), e);
        }
    }
}
