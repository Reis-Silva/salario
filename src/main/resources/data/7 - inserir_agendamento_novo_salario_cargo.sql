CREATE TABLE agendamento_novo_salario_cargo (
id BIGSERIAL PRIMARY KEY,
pessoa_id BIGINT NOT NULL,
cargo_atual_id BIGINT NOT NULL,
cargo_troca_id BIGINT NOT NULL,

    CONSTRAINT fk_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa (id),
    CONSTRAINT fk_cargo_atual FOREIGN KEY (cargo_atual_id) REFERENCES cargo (id),
    CONSTRAINT fk_cargo_troca FOREIGN KEY (cargo_troca_id) REFERENCES cargo (id)
);