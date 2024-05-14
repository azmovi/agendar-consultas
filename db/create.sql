drop database if exists sistema_de_agendamentos;

create database sistema_de_agendamentos;

use sistema_de_agendamentos;

create table usuario(
    id_profissinal bigint not null auto_increment PRIMARY KEY,
    nome varchar(50) not null,
    email varchar(50) not null,
    senha varchar(50) not null,
    cpf varchar(11) not null,
)

create table profissionais(
    id_profissinal bigint not null auto_increment PRIMARY KEY,
    especialidade varchar(50) not null,
    pdf longblob not null,
)

create table clientes(
    id_clinte bigint not null auto_increment PRIMARY KEY,
    sexo enum('masculino', 'feminimo', 'outro') not null,
    data_nascimento date not null,
)

create table agendamento (
    id_agendamento bigint not null PRIMARY KEY,
    cpf_cliente varchar(50),
    cpf_profissional varchar(50),
    data_agendamento date not null,
    hora_agendamento time not null,

    FOREIGN KEY(cpf_cliente) REFERENCES clientes(cpf)
    FOREIGN KEY(cpf_profissional) REFERENCES profissionais(cpf)
);

quit
