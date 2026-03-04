create table medicos (
    id bigint not null auto_increment primary key,
    nome varchar(255) not null,
    email varchar(255) not null unique,
    crm varchar(255) not null unique,
    especialidade varchar(255) not null,
    logradouro varchar(255) not null,
    numero varchar(255) not null,
    complemento varchar(255),
    bairro varchar(255) not null,
    cep varchar(9) not null,
    cidade varchar(100) not null,
    uf varchar(2) not null
);