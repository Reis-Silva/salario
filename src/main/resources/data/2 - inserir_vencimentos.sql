CREATE TABLE vencimentos (
id SERIAL PRIMARY KEY,
descricao VARCHAR(150) NOT NULL,
valor NUMERIC(10,2) NOT NULL,
tipo VARCHAR(10) CHECK (tipo IN ('CREDITO', 'DEBITO')) NOT NULL
);

-- Inserção dos dados
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (1, 'Vencimento Basico Estagiario', 400, 'CREDITO');
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (2, 'Vencimento Basico Tecnico', 1000, 'CREDITO');
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (3, 'Vencimento Basico Analista', 2500, 'CREDITO');
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (4, 'Vencimento Basico Coordenador', 5000, 'CREDITO');
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (5, 'Vencimento Basico Gerente', 6500, 'CREDITO');
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (6, 'Gratificacao Coordenador', 500, 'CREDITO');
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (7, 'Gratificacao Gerente', 1000, 'CREDITO');
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (8, 'Plano de Saude', 350, 'DEBITO');
INSERT INTO vencimentos (id, descricao, valor, tipo) VALUES (9, 'Previdencia', 11, 'DEBITO');