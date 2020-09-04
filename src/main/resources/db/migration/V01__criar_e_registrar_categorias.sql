CREATE TABLE categoria(
    id SERIAL not null,
    nome varchar(50) not null,
    PRIMARY KEY (id)
);

INSERT INTO categoria(nome)values('Lazer');
INSERT INTO categoria(nome)values('Alimentação');
INSERT INTO categoria(nome)values('Supermercado');
INSERT INTO categoria(nome)values('Farmácia');
INSERT INTO categoria(nome)values('Outros');