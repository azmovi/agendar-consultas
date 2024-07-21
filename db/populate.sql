use sistema_de_agendamentos;

INSERT INTO Usuario (nome, email, senha, cpf) VALUES 
('Alice Silva', 'alice@example.com', '123', '12345678901'),
('yop', 'alt.cl-2ohaymqp@yopmail.com', '123', '23456789012'),
('Lucas Velloso', 'velloso@example.com', '123', '34567890123'),
('Antonio Cicero', 'antonio@example.com', '123', '45678901234'),
('Yuki Inumaru', 'yuki@example.com', '123', '56789012345'),
('Lucas Roberto', 'lucas@example.com', '123', '67890123456');

INSERT INTO Cliente (id_usuario, sexo, data_nascimento) VALUES 
(1, 'FEMININO', '1990-01-01'),
(2, 'MASCULINO', '1985-05-15'),
(3, 'MASCULINO', '2000-12-30');

INSERT INTO Profissional (id_usuario, especialidade) VALUES 
(4, 'Fisioterapeuta'),
(5, 'Nutricionista'),
(6, 'Psicólogo');

INSERT INTO Agendamento (id_usuario_cliente, id_usuario_profissional, data, horario) VALUES 
(1, 4, '2024-08-01', '10:00:00'),
(2, 5, '2024-08-02', '11:00:00'),
(3, 6, '2024-08-03', '12:00:00');

exit
