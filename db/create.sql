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
    id_usuario bigint not null,
    especialidade varchar(50) not null,
    pdf longblob not null,
    FOREIGN KEY(id_usuario) REFERENCES usuarios(id_usuario)
)

create table clientes(
    id_cliente bigint not null auto_increment PRIMARY KEY,
    id_usuario bigint not null,
    sexo enum('MASCULINO', 'FEMINIMO', 'OUTRO') not null,
    data_nascimento date not null,
    FOREIGN KEY(id_usuario) REFERENCES usuarios(id_usuario)
)

create table agendamento (
    id_agendamento bigint not null PRIMARY KEY,
    id_cliente varchar(50),
    id_profissinal varchar(50),
    data_agendamento date not null,
    hora_agendamento time not null,

    FOREIGN KEY(id_cliente) REFERENCES clientes(id_cliente)
    FOREIGN KEY(id_profissinal) REFERENCES profissionais(id_profissinal)
);

quit
