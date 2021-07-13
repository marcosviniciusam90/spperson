INSERT INTO tb_pessoa (nome, sexo, email, data_Nascimento, naturalidade, nacionalidade, cpf, criado_Em) VALUES ('Marcos Vinicius', 'MASCULINO', 'marcosviniciusam90@gmail.com', '1990-10-19', 'Londrina-PR', 'brasileiro', '899.317.520-92', NOW());
INSERT INTO tb_pessoa (nome, sexo, email, data_Nascimento, naturalidade, nacionalidade, cpf, criado_Em) VALUES ('Antonina Rocha', 'FEMININO', 'antonina@gmail.com', '1966-10-19', 'Colorado-PR', 'brasileiro', '795.884.570-76', NOW());
INSERT INTO tb_pessoa (nome, sexo, email, data_Nascimento, naturalidade, nacionalidade, cpf, criado_Em) VALUES ('Marcela Almeida', 'FEMININO', 'marcelaalmeida@gmail.com', '1993-10-19', 'Colorado-PR', 'brasileiro', '187.727.740-10', NOW());
INSERT INTO tb_pessoa (nome, sexo, email, data_Nascimento, naturalidade, nacionalidade, cpf, criado_Em) VALUES ('Antonio Geraldo', 'MASCULINO', 'antoniogm@gmail.com', '1950-10-19', 'Londrina-PR', 'brasileiro', '306.609.320-40', NOW());
INSERT INTO tb_pessoa (nome, sexo, email, data_Nascimento, naturalidade, nacionalidade, cpf, criado_Em) VALUES ('Vaniele Fernanda', 'FEMININO', 'vanielef@gmail.com', '1997-10-19', 'Colorado-PR', 'brasileiro', '877.964.770-74', NOW());

INSERT INTO tb_usuario (id, nome, usuario, senha) values (1, 'Administrador', 'admin', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO tb_usuario (id, nome, usuario, senha) values (2, 'Marcos Vinicius', 'marcos', '$2a$10$wDtma4zDoUXx7Rdt096z..JQTVO1Aob6OzOcYg.6mIw3IVgsMwgiS');

INSERT INTO tb_permissao (id, descricao) values (1, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO tb_permissao (id, descricao) values (2, 'ROLE_REMOVER_PESSOA');
INSERT INTO tb_permissao (id, descricao) values (3, 'ROLE_PESQUISAR_PESSOA');

-- admin
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 2);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 3);

-- marcos
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (2, 3);