package br.com.esig.salario.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum VencimentoEnum {

    VENCIMENTO_BASICO_ESTAGIARIO(1, "Vencimento Basico Estagiario", new BigDecimal("400"), Tipo.CREDITO),
    VENCIMENTO_BASICO_TECNICO(2, "Vencimento Basico Tecnico", new BigDecimal("1000"), Tipo.CREDITO),
    VENCIMENTO_BASICO_ANALISTA(3, "Vencimento Basico Analista", new BigDecimal("2500"), Tipo.CREDITO),
    VENCIMENTO_BASICO_COORDENADOR(4, "Vencimento Basico Coordenador", new BigDecimal("5000"), Tipo.CREDITO),
    VENCIMENTO_BASICO_GERENTE(5, "Vencimento Basico Gerente", new BigDecimal("6500"), Tipo.CREDITO),
    GRATIFICACAO_COORDENADOR(6, "Gratificacao Coordenador", new BigDecimal("500"), Tipo.CREDITO),
    GRATIFICACAO_GERENTE(7, "Gratificacao Gerente", new BigDecimal("1000"), Tipo.CREDITO),
    PLANO_DE_SAUDE(8, "Plano de Saude", new BigDecimal("350"), Tipo.DEBITO),
    PREVIDENCIA(9, "Previdencia", new BigDecimal("11"), Tipo.DEBITO);

    private final int id;
    private final String descricao;
    private final BigDecimal valor;
    private final Tipo tipo;

    VencimentoEnum(int id, String descricao, BigDecimal valor, Tipo tipo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
    }

    public enum Tipo {
        CREDITO,
        DEBITO
    }
}
