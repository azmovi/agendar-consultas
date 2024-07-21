use sistema_de_agendamentos;

INSERT INTO Usuario (nome, email, senha, cpf) VALUES 
('Alice Silva', 'alice@example.com', '123', '12345678901'),
('Bruno Lima', 'bruno@example.com', '123', '23456789012'),
('Lucas Velloso', 'velloso@example.com', '123', '34567890123'),
('Antonio Cicero', 'antonio@example.com', '123', '45678901234'),
('Yuki Inumaru', 'yuki@example.com', '123', '56789012345'),
('Lucas Roberto', 'lucas@example.com', '123', '67890123456');

INSERT INTO Cliente (id_usuario, sexo, data_nascimento) VALUES 
(1, 'FEMININO', '1990-01-01'),
(2, 'MASCULINO', '1985-05-15'),
(3, 'MASCULINO', '2000-12-30');

INSERT INTO Profissional (id_usuario, especialidade) VALUES 
(4, 'Fisioterapeuta')
(5, 'Nutricionista')
(6, 'Psicólogo')

