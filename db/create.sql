drop database if exists sistema_de_agendamentos;

create database sistema_de_agendamentos;

use sistema_de_agendamentos;

create table Usuario(
    id_usuario bigint not null auto_increment PRIMARY KEY,
    nome varchar(50) not null,
    email varchar(50) not null,
    senha varchar(50) not null,
    cpf varchar(11) not null
);

create table Profissional(
    id_profissional bigint not null auto_increment PRIMARY KEY,
    id_usuario bigint not null,
    especialidade varchar(50) not null,
    pdf_data longblob not null,
    FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);

create table Cliente(
    id_cliente bigint not null auto_increment PRIMARY KEY,
    id_usuario bigint not null,
    sexo enum('MASCULINO', 'FEMINIMO', 'OUTRO') not null,
    data_nascimento date not null,
    FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);

create table Agendamento (
    id_agendamento bigint not null PRIMARY KEY,
    id_cliente bigint,
    id_profissional bigint,
    data_agendamento date not null,
    hora_agendamento time not null,
    FOREIGN KEY(id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY(id_profissional) REFERENCES Profissional(id_profissional)
);

quit

