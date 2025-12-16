CREATE DATABASE sistema_usuarios;
USE sistema_usuarios;

CREATE TABLE usuario (
    id INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

INSERT INTO usuario VALUES (1, 'andrey', '1313', 'ADMIN');

INSERT INTO usuario VALUES (2, 'rodrigues', '1234', 'CLIENTE');