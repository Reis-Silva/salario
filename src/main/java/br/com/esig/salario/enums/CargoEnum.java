package br.com.esig.salario.enums;

public enum CargoEnum {

    ESTAGIARIO(1, "Estagiario"),
    TECNICO(2, "Tecnico"),
    ANALISTA(3, "Analista"),
    COORDENADOR(4, "Coordenador"),
    GERENTE(5, "Gerente");

    private final int id;
    private final String descricao;

    CargoEnum(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CargoEnum fromId(int id) {
        for (CargoEnum cargo : CargoEnum.values()) {
            if (cargo.getId() == id) {
                return cargo;
            }
        }
        throw new IllegalArgumentException("ID inválido: " + id);
    }
}
