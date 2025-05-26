CREATE TABLE cargo (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Inserção dos dados
INSERT INTO cargo (id, nome) VALUES (1, 'Estagiario');
INSERT INTO cargo (id, nome) VALUES (2, 'Tecnico');
INSERT INTO cargo (id, nome) VALUES (3, 'Analista');
INSERT INTO cargo (id, nome) VALUES (4, 'Coordenador');
INSERT INTO cargo (id, nome) VALUES (5, 'Gerente');