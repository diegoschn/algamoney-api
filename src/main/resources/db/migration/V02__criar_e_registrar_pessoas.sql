CREATE TABLE pessoa(

    id SERIAL not null,
    nome varchar(50) not null,
    ativo boolean not null,
    PRIMARY KEY (id)
);

insert into pessoa(nome,ativo)values('Pessoa1',true);
insert into pessoa(nome,ativo)values('Pessoa2',false);
insert into pessoa(nome,ativo)values('Pessoa3',true);
insert into pessoa(nome,ativo)values('Pessoa4',false);