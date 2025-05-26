CREATE TABLE cargo_vencimento (
id SERIAL PRIMARY KEY,
cargo_id INT NOT NULL,
vencimento_id INT NOT NULL,
CONSTRAINT fk_cargo FOREIGN KEY (cargo_id) REFERENCES cargo(id),
CONSTRAINT fk_vencimento FOREIGN KEY (vencimento_id) REFERENCES vencimentos(id)
);

-- Inserção dos dados
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (1, 1, 1);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (2, 2, 2);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (3, 2, 9);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (4, 2, 8);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (5, 3, 3);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (6, 3, 9);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (7, 3, 8);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (8, 4, 4);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (9, 4, 9);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (10, 4, 8);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (11, 4, 6);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (12, 5, 5);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (13, 5, 9);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (14, 5, 8);
INSERT INTO cargo_vencimento (id, cargo_id, vencimento_id) VALUES (15, 5, 7);